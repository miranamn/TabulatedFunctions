package functions;
import java.io.Serializable;
import java.util.Iterator;

public class LinkedListTabulatedFunction implements TabulatedFunction, Serializable {

    static public class FunctionNode {
        private FunctionPoint onePoint; //поле списка
        private FunctionNode next;
        private FunctionNode prev;

        public FunctionNode() {
            next = this;
            prev = this;
        }
    }

    private FunctionNode head; //голова списка
    private int n;

    private LinkedListTabulatedFunction(int amount) {
        this.head = new FunctionNode();
        FunctionNode tempNode = this.head;
        while (amount-- > 0) {
            tempNode = tempNode.next = this.addNodeToTail();
        }
    }

    public LinkedListTabulatedFunction(FunctionPoint[] point) {
        this(point.length);
        if (point.length <= 2)
            throw new IllegalArgumentException(); //исключение
        else {
            FunctionPoint StartPoint = point[0];
            for (int i = 1; i < point.length; i++) {
                if (point[i].getX() < StartPoint.getX()) throw new IllegalArgumentException();
                else StartPoint = point[i];
            }
        }
        n = point.length;
        for (int i = 0; i < point.length; i++)
            this.getNodeByIndex(i).onePoint = point[i];
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, int pointsCount) { //конструктор первый
        this(pointsCount);
        if (leftX >= rightX || pointsCount < 2) throw new IllegalArgumentException();
        n = pointsCount;
        double interval = (rightX - leftX) / (pointsCount - 1);
        for (int i = 0; i < pointsCount; i++)
            this.getNodeByIndex(i).onePoint = new FunctionPoint(leftX + i * interval, 0);
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, double[] values) { //конструктор второй
        this(values.length);
        if (leftX >= rightX || values.length < 2) throw new IllegalArgumentException();
        n = values.length;
        double interval = (rightX - leftX) / (values.length - 1);
        for (int i = 0; i < values.length; i++)
            this.getNodeByIndex(i).onePoint = new FunctionPoint(leftX + i * interval, values[i]);
    }

    //вспомогательные методы
    private FunctionNode getNodeByIndex(int index) {
        FunctionNode temp = head.next;
        for (int i = 0; i < index; i++)
            temp = temp.next;
        return temp;
    }

    private FunctionNode addNodeToTail() {
        FunctionNode temp = this.head;
        for (int i = 0; i < this.n; i++)
            temp = temp.next;
        temp.next = new FunctionNode();
        if (temp != this.head)
            temp.next.prev = temp;
        temp.next.next = this.head.next;
        n++;
        return temp.next;
    }

    private FunctionNode addNodeByIndex(int index) {
        FunctionNode newNode = new FunctionNode();
        FunctionNode prevNode;
        FunctionNode nextNode;
        if (index > 0) {
            prevNode = getNodeByIndex(index - 1);
            nextNode = prevNode.next;
            newNode.prev = prevNode;
            newNode.next = nextNode;
            prevNode.next = nextNode;
            newNode.prev = nextNode;
        } else if (index == 0) {
            if (n > 0) {
                nextNode = this.head.next;
                prevNode = this.getNodeByIndex(n - 1);
                newNode.prev = prevNode;
                newNode.next = nextNode;
                this.head.next = newNode;
                prevNode.next = nextNode;
                nextNode.prev = newNode;
            } else
                this.head = newNode;
        } else
            return null;
        return newNode;
    }

    private FunctionNode deleteNodeByIndex(int index) {
        FunctionNode nodeToBeDeleted = this.getNodeByIndex(index);
        FunctionNode prevNode = nodeToBeDeleted.prev;
        FunctionNode nextNode = nodeToBeDeleted.next;
        if (nodeToBeDeleted != null) {
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
            if (index == 0)
                this.head.next = nextNode;
        }
        return nodeToBeDeleted;
    }
    //методы класса табулированной функции

    public double getLeftDomainBorder() {
        return head.next.onePoint.getX();
    }

    public double getRightDomainBorder() {
        return getNodeByIndex(n - 1).onePoint.getX();
    }

    public double getFunctionValue(double x) {
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()) //проверка на границу
            return Double.NaN;
        else {
            FunctionNode nxt = head.next;
            while (!(nxt.onePoint.getX() <= x && x <= nxt.next.onePoint.getX()))
                nxt = nxt.next;
            if (nxt.onePoint.getX() == x)
                return nxt.onePoint.getY();
            if (nxt.next.onePoint.getX() == x)
                return nxt.next.onePoint.getY();
            //y=kx+b
            double ff, k, b;
            k = (nxt.next.onePoint.getY() - nxt.onePoint.getY()) / (nxt.next.onePoint.getX() - nxt.onePoint.getX());
            b = nxt.onePoint.getY() - k * nxt.onePoint.getX();
            ff = k * x + b;
            return ff;
        }
    }

    public int getPointsCount() {
        return n;
    }

    public FunctionPoint getPoint(int index) {
        if (index < 0 || index >= n) {
            throw new FunctionPointIndexOutOfBoundsException();
        } else
            return getNodeByIndex(index).onePoint;
    }

    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        if (index < 0 || index >= n) {
            throw new FunctionPointIndexOutOfBoundsException();
        } else {
            FunctionNode temp = getNodeByIndex(index);
            if ((temp.prev.onePoint.getX() < point.getX()) && (point.getX() < temp.next.onePoint.getX()))
                temp.onePoint = point;
            else
                throw new InappropriateFunctionPointException();
        }
    }

    public double getPointX(int index) {
        if (index < 0 || index >= n)
            throw new FunctionPointIndexOutOfBoundsException();
        return getNodeByIndex(index).onePoint.getX();
    }

    public double getPointY(int index) {
        if (index < 0 || index >= n)
            throw new FunctionPointIndexOutOfBoundsException();
        return getNodeByIndex(index).onePoint.getY();
    }

    public void setPointX(int index, double x) throws InappropriateFunctionPointException {
        if (index < 0 || index >= n)
            throw new FunctionPointIndexOutOfBoundsException();
        else {
            FunctionNode temp = getNodeByIndex(index);
            if ((temp.prev.onePoint.getX() < x) && (x < temp.next.onePoint.getX())) {
                FunctionPoint pointToo = new FunctionPoint(x, temp.onePoint.getY());
                temp.onePoint = pointToo;
            } else throw new InappropriateFunctionPointException();
        }
    }

    public void setPointY(int index, double y) {
        if (index < 0 || index > n)
            throw new FunctionPointIndexOutOfBoundsException();
        else {
            FunctionPoint pointToo = new FunctionPoint(getNodeByIndex(index).onePoint.getX(), y);
            getNodeByIndex(index).onePoint = pointToo;
        }
    }

    public void deletePoint(int index) {
        if (n > 2) {
            if (index >= 0 && index < n - 1)
                this.deleteNodeByIndex(index);
            else if (index == n - 1)
                this.deleteNodeByIndex(index);
            else
                throw new FunctionPointIndexOutOfBoundsException();
            n--;
        }
    }

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        FunctionNode temp = head;
        int currentPoint = -1;
        if ((temp != head) && (point.getX() == temp.onePoint.getX()))
            throw new InappropriateFunctionPointException();
        do {
            ++currentPoint;
            temp = temp.next;
        }
        while ((temp != head) && (temp.onePoint.getX() < point.getX()));
        try {
            temp = addNodeByIndex(currentPoint);
            temp.onePoint = point;
        } catch (FunctionPointIndexOutOfBoundsException e) {
            temp = addNodeToTail();
            temp.onePoint = point;
        }
        ++n;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        for (int i = 0; i < getPointsCount(); i++) {
            stringBuilder.append("( ").append(getPointX(i)).append("; ").append(this.getPointY(i)).append(")");
        }
        return stringBuilder + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TabulatedFunction)) return false;
        if (o instanceof ArrayTabulatedFunction) {
            ArrayTabulatedFunction temp1 = (ArrayTabulatedFunction) o;
            for (int i = 0; i < getPointsCount(); i++)
                if (!getPoint(i).equals(temp1.getPoint(i))) return false;
        } else {
            TabulatedFunction temp2 = (TabulatedFunction) o;
            for (int i = 0; i < getPointsCount(); i++)
                if (!getPoint(i).equals(temp2.getPoint(i))) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        FunctionNode temp = head.next;
        int res = 1;
        while (temp.next != head.next) {
            res = 31 * res + temp.onePoint.hashCode();
            temp = temp.next;
        }
        return getPointsCount() + 31 * res;
    }


    public Object clone() {
        LinkedListTabulatedFunction temp;
        try {
            temp = (LinkedListTabulatedFunction)super.clone();
            FunctionNode curr1 = head.next;
            FunctionNode curr2;
            temp.head.onePoint = (FunctionPoint)curr1.next.onePoint.clone();
            curr1 = curr1.next;
            curr2 = temp.head.next;
            while (curr1.next != head.next.next) {
                curr2.next = new FunctionNode();
                curr2.next.onePoint = (FunctionPoint)curr1.onePoint.clone();
                curr1 = curr1.next;
                curr2 = curr2.next;
            }
            return temp;
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }

    @Override
    public Iterator<FunctionPoint> iterator() {
        return new getIterator();
    }

    private class getIterator implements Iterator<FunctionPoint> {
        int index;

        @Override
        public boolean hasNext() {
            return index < n;
        }

        @Override
        public FunctionPoint next() {
            return getNodeByIndex(index++).onePoint;
        }
    }

    public static class LinkedListTabulatedFunctionFactory implements TabulatedFunctionFactory{
        public TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values){
            return new LinkedListTabulatedFunction(leftX, rightX, values);
        }

        public TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount){
            return new LinkedListTabulatedFunction(leftX, rightX, pointsCount);
        }

        public TabulatedFunction createTabulatedFunction(FunctionPoint[] point){
            return new LinkedListTabulatedFunction(point);
        }

    }
}

