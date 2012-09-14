
public class Foo {

    protected String fooStr;

    public Foo(String fooStr) {
        this.fooStr = fooStr;
    }

    private void innerDoSth() {
        System.out.println("Hello, " + this.fooStr);
    }

    public void doSomething() {
        System.out.println("doing something...");
        innerDoSth();
    }

    public static void main(String[] args) {
        new Foo("hey").doSomething();
    }
    
}
