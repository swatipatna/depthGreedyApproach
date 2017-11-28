import java.util.ArrayList;
import java.util.Arrays;

public class Depth {
  public static void main(String[] args) {
    // Creating schdules with starting times and end times
    Schedule schedule1 = new Schedule(1, 3);
    Schedule schedule2 = new Schedule(2, 5);
    Schedule schedule3 = new Schedule(6, 7);
    Schedule schedule4 = new Schedule(4, 8);
    Schedule[] schedules = { schedule1, schedule2, schedule3, schedule4 };

    // Print out the maximum amount of machines in use
    printMachinesUsed(schedules);
  }

  public static void printMachinesUsed(Schedule[] schedules) {
    // Sort schedules by start time
    // Big-O: O(N Log N) 
    Arrays.sort(schedules);

    // Create an array of integers to represend machines, where:
    // 1) each element of the array represents a machine and
    // 2) the value store in each element is equal to the 
    //    ending time of the last job queued up on that machine
    ArrayList<Integer> machines = new ArrayList<Integer>();

    // Check if we can add schedules to machines currently running
    // or if we need to start new machines to accommodate the schedules
    //
    // Big-O: O(N*D), where N = number of jobs and D = number of machines
    for (int i = 0; i < schedules.length; i++) {
      boolean addedToExistingMachine = false;

      // Loop through the machines running and check if we can  
      // add this schedule to any of the machines currently running
      for (int j = 0; j < machines.size(); j++) {
        // if this schedule's start time is later than the ending time 
        // of the last job queued up in this machine. We can add this schedule 
        // to this machine
        if (machines.get(j) < schedules[i].getStartTime()) {
          machines.set(j, schedules[i].getEndTime());
          addedToExistingMachine = true;

          // print out that we added this schedule to this machine
          printMachineAndSchedule(j, schedules[i]);
        }
      }

      // We can't add this schedule to any machines currently running because of conflicts
      // or no machines are running. Therefore, we need to start a new machine
      if (!addedToExistingMachine) {
        machines.add(schedules[i].getEndTime());

        // print out that we added this schedule to a new machine
        printMachineAndSchedule(machines.size() - 1, schedules[i]);
      }
    }

    // print out number of machines used 
    System.out.println("Number of machines used: " + machines.size());
  }

  public static void printMachineAndSchedule(int machineId, Schedule schedule) {
    System.out.print("Machine " + machineId + " scheduled: ");
    System.out.println(schedule.toString());
  }
}

class Schedule implements Comparable<Schedule> {
  private int startTime;
  private int endTime;

  public Schedule(int startTime, int endTime) {
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public int getStartTime() {
    return this.startTime;
  }

  public int getEndTime() {
    return this.endTime;
  }

  public int compareTo(Schedule otherSchedule) {
    // sort by the schedule's start time from earliest to latest
    return this.getStartTime() - otherSchedule.getStartTime();
  }

  public String toString() {
    return "{" + this.startTime + ", " + this.endTime + "}";
  }
}