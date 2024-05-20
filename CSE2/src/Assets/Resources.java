package Assets;

public class Resources {
    private int cpuCores;
    private int memory;
    private int disk;
    private double price;

    //cpu: number of cores
    //memory: 100 Mbyte
    //disk: 1 Gbyte

    public Resources(int cpuCores, int memory, int disk) {
        this.cpuCores = cpuCores;
        this.memory = memory;
        this.disk = disk;
        this.price = calculatePrice();
    }

    private double calculatePrice() {
        return (cpuCores * 0.5 + memory * 2 + disk * 0.2);
    }

    @Override
    public String toString() {
        return "Resources{" +
                "cpuCores=" + cpuCores +
                ", memory=" + memory +
                ", disk=" + disk +
                ", price=" + price +
                '}';
    }


    // Getters and setters for the private variables

    public int getCpuCores() {
        return cpuCores;
    }

    public int getMemory() {
        return memory;
    }

    public int getDisk() {
        return disk;
    }

    public double getPrice() {
        return price;
    }
}