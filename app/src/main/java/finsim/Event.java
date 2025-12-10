package finsim;

public abstract class Event {
    private final String name;
    private final Purpose purpose;

    public Event(String name, Purpose purpose) {
        this.name = name;
        this.purpose = purpose;
    }

    public String getName() {
        return name;
    }

    public Purpose getPurpose() {
        return purpose;
    }

    public abstract void run(int month, Accounts accounts);

    public enum Purpose {
        Growth(0),
        Income(1),
        PreTax(2),
        IncomeTax(3),
        PreExpense(4),
        PostTax(5),
        PostExpense(6);

        private final int order;

        Purpose(int order) {
            this.order = order;
        }

        public int getOrder() {
            return order;
        }
    }
}
