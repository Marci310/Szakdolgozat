package Resources;

public class Resources
{
    private int cpuCores;
    private int memory;
    private int disk;

    //cpu: number of cores
    //memory: 100 Mbyte
    //disk: 1 Gbyte

    public Resources(int cpuCores, int memory, int disk)
    {
        this.cpuCores = cpuCores;
        this.memory = memory;
        this.disk = disk;
    }

    // Getters and setters for the private variables

    public int getCpuCores()
    {
        return cpuCores;
    }

    public void setCpuCores(int cpuCores)
    {
        this.cpuCores = cpuCores;
    }

    public int getMemory()
    {
        return memory;
    }

    public void setMemory(int memory)
    {
        this.memory = memory;
    }

    public int getDisk()
    {
        return disk;
    }

    public void setDisk(int disk)
    {
        this.disk = disk;
    }

    public int getSize()
    {
        return cpuCores*memory*disk;
    }
}