public class Dessert {
    private int flavor;

    private int price;

    private static int numDesserts;

    public Dessert(int f, int p)
    {
        this.flavor = f;
        this.price = p;
        numDesserts++;
    }

    public void printDessert()
    {
        System.out.printf("%d %d %d\n", this.flavor, this.price, this.numDesserts);
    }

    public static void main(String args[])
    {
        System.out.println("I love dessert!");
    }
}
