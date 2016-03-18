
import java.util.*;


public class RR {
	public static double time;
	public static double maxTime = 100;
	public static double startTime;
	public static double endTime;
	public static double IOTs = 0.01; 
	public static double CPUTs = 0.3; 
	public static double lambda = 9;
	public static boolean busy = true;
    public static boolean idle = false;
    public static int numInQueue;
    public static boolean Server;
    public static double sumTw = 0;
    public static double sumTq = 0;
    public static double arrTime;
    public static double sumIOTs;
    public static double sumCPUTs;
    public static double sumIOTq = 0;
    public static double sumCPUTq = 0;
    public static boolean interrupt = false;
	
	public static Queue customers;
	public static Heap schedule;
	public static final int BIRTH = 1;
    public static final int DEATH = 0;
    public static int count = 0;
    public static int IOcount = 0;
    public static int CPUcount = 0;
    public static boolean isIO = false;
    public static double ios=0;
    public static double cpus = 0;
    
	
	//initialize the system
    public static void initialize(){
        time = 0;
        Server = idle;
        startTime = 0;
        endTime = 0;
        customers = new Queue();
        numInQueue = 0;
        Event evt = new Event(time, BIRTH,0.0);
        schedule = new Heap();
        schedule.enqueue(evt);
    }
	
	//use random number to generate service time
	public static double IOserviceTime(){
	       double lambda = 1 / IOTs;
	       return (- (Math.log(1.0 - Math.random()) / lambda));
	}
	
	public static double CPUserviceTime(){
	       double lambda = 1 / CPUTs;
	       return (- (Math.log(1.0 - Math.random()) / lambda));
	}
	
	//generate interarrival time
	public static double interArr(){
        return (- (Math.log(1.0 - Math.random()) / lambda));
    }
	
	public static void arrive(Event evt, boolean record){
        count++;
        if(Math.random()<(1.0/3.0)){
        	IOcount++;
        	isIO = true;
        }else{
        	CPUcount++;
        	isIO = false;
        }
        customers.enqueue(evt);                                
        numInQueue+=1;
        arrTime = evt.time();
        double nextTime = time+interArr();
        Event nextBirth = new Event(nextTime, BIRTH,0.0);      
        schedule.enqueue(nextBirth);
        
        if (Server==idle){                                 
            service(record);                               
            Event nextDeath = new Event(endTime, DEATH,0.0);   
            schedule.enqueue(nextDeath);
            time = endTime;
        }
    }
	
	public static void service(boolean record){
		 //dequeue the event 
        Event evt = (Event) customers.dequeue();            
        numInQueue--;
        Server = busy;
        startTime = time;
        if(evt.timeleft() >= 0){
        	if(evt.timeleft() > 0.1){
        		if(isIO){
            		Event notDone = new Event(evt.time(),evt.type(),evt.timeleft()-0.1);
            		customers.enqueue(notDone);
            		numInQueue++;
            		sumIOTs += 0.1;
            		endTime = startTime + 0.1;
            	}else{
            		Event notDone = new Event(evt.time(),evt.type(),evt.timeleft()-0.1);
            		customers.enqueue(notDone);
            		numInQueue++;
            		sumCPUTs += 0.1;
            		endTime = startTime + 0.1;
            	}
        		
        	}else if(evt.timeleft() == 0){
        		if(isIO){
                	ios = IOserviceTime();
                	endTime = startTime + ios;
                }else{
                	cpus = CPUserviceTime();
                	endTime = startTime + cpus;
                }
        		 if(endTime > startTime+0.1){
                 	if(isIO){
                 		Event notDone = new Event(evt.time(),evt.type(),ios-0.1);
                 		customers.enqueue(notDone);
                 		numInQueue++;
                 		sumIOTs += 0.1;
                 		endTime = startTime + 0.1;
                 	}else{
                 		Event notDone = new Event(evt.time(),evt.type(),cpus-0.1);
                 		customers.enqueue(notDone);
                 		numInQueue++;
                 		sumCPUTs += 0.1;
                 		endTime = startTime + 0.1;
                 	}
                 }
        	
        	}else{
        		if(isIO){
                	sumIOTs += evt.timeleft();
                	endTime = startTime + ios;
                }else{
                	
                	sumCPUTs += evt.timeleft();
                	endTime = startTime + cpus;
                }
        		
        	}
        }
       
        
        
        if (record) {
        	if(isIO){
        		sumIOTq += (endTime - evt.time());
        	}else{
        		sumCPUTq += (endTime - evt.time());
        	}
            //sumTq += (endTime - evt.time());
            //sumTw += (startTime - evt.time());
        } 
    }
	
	

    
    public static void leave(boolean record){
        Server = idle;
     // check if there are any requests waiting in the queue
        if (numInQueue > 0){                      
            service(record);                       
            Event nextDeath = new Event(endTime, DEATH,0.0);          
            schedule.enqueue(nextDeath);
            time = endTime;
        }
        count--;
    }
    
    
    
    //Controller
    public static void main(String[] Args){
        
        //set up 
      
        initialize();
        
        //run
        boolean record = false;
        int numEvents = 0;
        int w = 0;
        int q = 0;
        while (time < maxTime){
            Event evt = (Event) schedule.dequeue();
            time = evt.time();
            if (evt.type() == BIRTH){
                arrive(evt, record);
            } else if (evt.type() == DEATH){
                leave(record);
            }
            if(time > (maxTime / 2)){            //allow the system to warm up
                w = w + numInQueue;
                q = q + count;
                numEvents++;
                record = true;
            }
        }
        
   
        System.out.println("w = " + ((double)w) / numEvents);
        System.out.println("q = " + ((double)q) / numEvents);
       // System.out.println("average Tw = " + sumTw / numEvents);
       // System.out.println("average Tq = " + sumTq / numEvents);
        System.out.println("count IO: " +IOcount+"\t count CPU: " + CPUcount);
        System.out.println("Slowdown for IO-bound is : " +(double)sumIOTq/(double)sumIOTs+"\t Slowdown for CPU-bound is : " + (double)sumCPUTq/(double)sumCPUTs);
    }
	

}

//attributes and methods of events
class Event implements Comparable<Event>{
    private double time;
    private int type;
    private double timeleft;
    public Event(double _time, int _type,double _timeleft){
        this.time = _time;
        this.type = _type;
        this.timeleft = _timeleft;
    }
    
    public double time(){
        return this.time;
    }
    public double timeleft(){
        return this.timeleft;
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
