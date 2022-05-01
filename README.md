
# Final Project Proposal

This project is an extention on current project 2. I'll be adding two additional features:
    
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
- [ ]  implement push-based brokers
- [ ]  set breakpoints for runtime measurement
- [ ]  experiment on pull-based broker and push-based broker


**By May 12**
- [ ]  experiment on number of partitions
- [ ]  experiment on number of producers
- [ ]  experiment on number of brokers
- [ ]  experiment on number of topics
- [ ]  experiment on randomly-generate partitionID VS. mod-generated partitionID


**By May 15**
- [ ]  generate graphs 
- [ ]  write short paper











