
import java.io.*;
import java.util.*;




public class SimFive1 {
	public static int k = 2;
	public static double lambda = 33;
	public static boolean busy = true;
	public static boolean idle = false;
	public static int failed = 0;
	public static Random rand;
	public static final double MaxTime = 100;
	
	public static final int cpuBirth = 0;
	public static final int cpuDeath1 = 1;
	public static final int cpuDeath2 = 2;
	public static final int diskBirth = 3;
	public static final int diskDeath = 4;
	public static final int netBirth = 5;
	public static final int netDeath = 6;
	public static final int snapshot = 7;
	public static final int gotoCpu = 8;
	
	public static double arrTime;
	public static double currentTime;
	public static double startTime;
	public static double cpuEndTime;
	public static double diskEndTime;
	public static double netEndTime;
	public static double sumTq = 0.0;
	public static double sumTs = 0.0;
	//public static double sumSqTq = 0.0;
	public static boolean cServer1;
	public static boolean cServer2;
	public static boolean disk;
	public static boolean net;
	public static boolean useS1;
	public static boolean useS2;
	
	public static Queue cpuQ;
	public static Queue diskQ;
	public static Queue netQ;
	public static int totalServed;
	public static Heap schedule;
	public static int totalNetReq;
	
	public static void initialize(){
		currentTime = 0;
		cServer1 = idle;
		cServer2 = idle;
		disk = idle;
		net = idle;
		startTime = 0;
		cpuEndTime = 0;
		diskEndTime = 0;
		netEndTime = 0;
		cpuQ = new Queue();
		diskQ = new Queue();
		netQ = new Queue();
		rand = new Random();
		schedule = new Heap();
		Event e  = new Event(currentTime, cpuBirth);
		schedule.enqueue(e);
		
		for(int i = 0; i < MaxTime;i++){
			Event monitor = new Event((double)i, snapshot);
			schedule.enqueue(monitor);
		}
		
	}
	public static double interArr(){
        return (- (Math.log(1.0 - Math.random()) / lambda));
    }
	
	public static double cpuServiceTime(){
		return (Math.random()/50.0) + 0.01;
	}
	
	public static double diskServiceTime(){
		double t = -1.0;
		while(t<0){
			t = rand.nextGaussian()*0.02+0.1;
		}
		return t;
	}
	
	public static double netServiceTime(){
		return 0.025;
	}
	
	public static void cpuService1(){
		Event e =(Event) cpuQ.dequeue();
		cServer1 = busy;
		startTime = currentTime;
		double cSt = cpuServiceTime();
		cpuEndTime = startTime + cSt;
		totalServed++;
		sumTq += (cpuEndTime - e.time());
		if(cSt *lambda >= 1.0){
			System.out.println("cpu1 burst!");
		}
		//sumSqTq += (cpuEndTime - e.time())*(cpuEndTime - e.time());
		
	}
	
	public static void cpuService2(){
		Event e =(Event) cpuQ.dequeue();
		cServer2 = busy;
		startTime = currentTime;
		double cSt = cpuServiceTime();
		cpuEndTime = startTime + cSt;
		totalServed++;
		sumTq += (cpuEndTime - e.time());
		if(cSt *lambda >= 1.0){
			System.out.println("cpu2 burst!");
		}
		//sumSqTq += (cpuEndTime - e.time())*(cpuEndTime - e.time());
		
	}
	
	public static void diskService(){
		Event e =(Event) diskQ.dequeue();
		disk = busy;
		double dSt = diskServiceTime();
		startTime = currentTime;
		diskEndTime = startTime + dSt;
		if(dSt * 0.2*lambda >= 1.0){
			System.out.println("disk burst!");
		}
	}
	
	public static void netService(){
		Event e =(Event) netQ.dequeue();
		net = busy;
		startTime = currentTime;
		double nSt = netServiceTime();
		netEndTime = startTime + nSt;
		if(nSt * 0.2*lambda >= 1.0){
			System.out.println("net burst!");
		}
	}
	
	public static void cpuArrival(Event e){
		cpuQ.enqueue(e);
		currentTime = e.time();
		if(e.type() == cpuBirth){
			double nextTime = currentTime + interArr();
			Event nextBirth = new Event(nextTime,cpuBirth);
			schedule.enqueue(nextBirth);
		}
		if(cServer1 == idle){
			cpuService1();
			currentTime = cpuEndTime;
			Event nextDeath = new Event(currentTime,cpuDeath1);
			schedule.enqueue(nextDeath);
		}else if(cServer2 == idle){
			cpuService2();
			currentTime = cpuEndTime;
			Event nextDeath = new Event(currentTime,cpuDeath2);
			schedule.enqueue(nextDeath);
		}
	}
	
	public static void diskArrival(Event e){
		diskQ.enqueue(e);
		arrTime = e.time();
		if(disk == idle){
			diskService();
			currentTime = diskEndTime;
			Event nextDeath = new Event(currentTime,diskDeath);
			schedule.enqueue(nextDeath);
		}
	}
	
	public static void netArrival(Event e){
		totalNetReq++;
		if(netQ.size()<k){
			netQ.enqueue(e);
		}else{
			failed++;
		}
		if(net == idle){
			netService();
			currentTime = netEndTime;
			Event nextDeath = new Event(currentTime,netDeath);
			schedule.enqueue(nextDeath);
		}
	}
	
	public static void cpuLeave(Event e){
		if(e.type() == cpuDeath1){
			cServer1 = idle;
		}else if(e.type() == cpuDeath2){
			cServer2 = idle;
		}
		double r = Math.random();
		currentTime = e.time();
		if(r < 0.1){
			Event nextDiskBirth = new Event(currentTime,diskBirth);
			schedule.enqueue(nextDiskBirth);
		}else if(r > 0.6){
			Event nextNetBirth = new Event(currentTime,netBirth);
			schedule.enqueue(nextNetBirth);
		}
		
		if(cpuQ.size() > 0){
			if(cServer1 == idle){
				cpuService1();
				currentTime = cpuEndTime;
				Event nextDeath = new Event(currentTime, cpuDeath1);
				schedule.enqueue(nextDeath);
			}else if(cServer2 == idle){
				cpuService2();
				currentTime = cpuEndTime;
				Event nextDeath = new Event(currentTime, cpuDeath2);
				schedule.enqueue(nextDeath);
			}
		}
	}
	
	public static void diskLeave(Event e){
		currentTime = e.time();
		disk = idle;
		double r = Math.random();
		if(r > 0.5){
			Event toCpu = new Event(currentTime, gotoCpu);
			schedule.enqueue(toCpu);
		}else{
			Event toNet = new Event(currentTime, netBirth);
			schedule.enqueue(toNet);
		}
		
		if(diskQ.size() > 0){
			diskService();
			currentTime = diskEndTime;
			Event nextDeath = new Event(currentTime, diskDeath);
			schedule.enqueue(nextDeath);
		}
		
	}
	
	public static void netLeave(Event e){
		currentTime = e.time();
		net = idle;
		Event toCpu = new Event(currentTime, gotoCpu);
		schedule.enqueue(toCpu);
		if(netQ.size() > 0){
			netService();
			currentTime = netEndTime;
			Event nextDeath = new Event(currentTime, netDeath);
			schedule.enqueue(nextDeath);
		}
	}
	
	public static void monite(){
		System.out.println("total num of net requests\t"+totalNetReq+"\t"+"num of failed:\t" + failed+"\t"+"percentage of failed requests\t"+((double)failed/(double)totalNetReq));
		
	}
	
	public static void main(String[] args){
		initialize();
		int count = 0;
		while(currentTime < MaxTime){
			Event e = (Event)schedule.dequeue();
			currentTime = e.time();
			if(e.type() == cpuBirth || e.type() == gotoCpu){
				cpuArrival(e);
			}else if(e.type() == cpuDeath1 || e.type() == cpuDeath2){
				cpuLeave(e);
			}
			
			if(e.type() == diskBirth){
				diskArrival(e);
			}else if(e.type() == diskDeath){
				netLeave(e);
			}
			if(e.type() == netBirth){
				netArrival(e);
			}else if(e.type() == netDeath){
				netLeave(e);
			}
			
			if(e.type() == snapshot){
				monite();
				count++;
			}
		}
		double meanTq = sumTq/totalServed;
		System.out.println("The percentage of failed requests is "+ (double)failed/(double)totalNetReq);
		System.out.println("The meanTq is : "+meanTq);
		//System.out.println("The slowdown is: "+ (double)sumTq/(double)sumTs);
		//System.out.println("The capacity is: "+ ((double)totalServed-(double) failed)/(double)MaxTime);
	}
	


}

class Event implements Comparable<Event>{
    private double time;
    private int type;
    public Event(double _time, int _type){
        this.time = _time;
        this.type = _type;
    }
    
    public double time(){
        return this.time;
    }
    
    public int compareTo(Event that){
        if (this.time < that.time){
            return -1;
        } else if (this.time == that.time){
            return 0;
        } else {
            return 1;
        }
    }
    
    public int type(){
        return this.type;
    }
    
}

class Queue extends LinkedList{
    public Queue(){
        super();
    }
    public void enqueue(Object obj){
        add(obj);
    }
    
    public Object dequeue(){
        return removeFirst();
    }
    
}

class Heap extends PriorityQueue{
    public Heap(){
        super();
    }
    public void enqueue(Object obj){
        add(obj);
    }
    
    public Object dequeue(){
        return poll();
    }
}