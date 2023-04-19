
# Pub-sub Distributed System Part2

This project is an extention on Pub-sub distributed system part1. 

You can find part 1 here: https://github.com/CS682-S22/distributed-system-part1-Jennytang1224

I'm adding two additional features in this project:
    
**1. adding push-based broker**

**2. implement performance analysis**

## Current System:
The existing program is a simple version of Kafka pub-sub system: 
 - Producers: will send data to all brokers through load balancer.
 - Load Balancer: will calculate the partition number based the "key" inside of data and pass data to the brokers.
 - Brokers: maintain their own topic map that stores data based on assigned partition number.
 - consumers: requesting data from brokers given the topic and starting position.

##  Push-based Broker
Currently I have pull-based broker with partition implemented. I will add a push-based version as this was a 10pt additional feature in project 2 that I haven't implemented.

##  Performance Analysis
**Variables:**
* pull-based broker and push-based broker
* number of partitions
* number of producers
* number of brokers
* number of topics
* randomly-generate partitionID VS. mod-generated partitionID

**Measurement:**  
will use runtime as measurement to compare result by testing on different variables

**Paper:**  
will producer a short paper to descirbe the result and analyze the data with graphs


## Milestones:
**By May 10**
- [x]  implement push-based brokers
- [x]  set breakpoints for runtime measurement
- [x]  experiment on pull-based broker and push-based broker


**By May 12**
- [x]  experiment on number of data
- [x]  experiment on number of partitions
- [x]  experiment on number of brokers
- [x]  experiment on randomly-generate partitionID VS. mod-generated partitionID
- [x]  experiment on consumer runtime
- [x]  experiment on loadbalancer runtime


**By May 15**
- [x]  generate graphs 
- [x]  write short paper











