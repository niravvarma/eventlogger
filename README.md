# Topic
Coding challenge by Credit Suisse for Java Developer role

## Challenge
Our custom-build server logs different events to a file named logfile.txt. 
Every event has 2 entries in the file - one entry when the event was started and another when the event was finished. 
The entries in the file have no specific order (a finish event could occur before a start event for a given id) 

Every line in the file is a JSON object containing the following event data:   
1. id - the unique event identifier   
2. state - whether the event was started or finished (can have values "STARTED" or "FINISHED"   
3. timestamp - the timestamp of the event in milliseconds Application Server logs also have the following additional attributes:   
4. type - type of log   
5. host - hostname 

Example contents of logfile.txt:

``
{"id":"scsmbstgra", "state":"STARTED", "type":"APPLICATION_LOG", "host":"12345", "timestamp":{"id":"scsmbstgrb", "state":"STARTED", "timestamp":1491377495213}
{"id":"scsmbstgrc", "state":"FINISHED", "timestamp":1491377495218}
{"id":"scsmbstgra", "state":"FINISHED", "type":"APPLICATION_LOG", "host":"12345", "timestamp":{"id":"scsmbstgrc", "state":"STARTED", "timestamp":1491377495210}
{"id":"scsmbstgrb", "state":"FINISHED", "timestamp":1491377495216}
``

... In the example above, the event scsmbstgrb duration is 1491377495216 - 1491377495213 = 3ms
The longest event is scsmbstgrc (1491377495218 - 1491377495210 = 8ms)

## The program should:
1. Have Unit Tests
2. Take the path to logfile.txt as an input argument
3. Parse the contents of logfile.txt
4. Flag any long events that take longer than 4ms
5. Write the found event details to file-based HSQLDB (http://hsqldb.org/) in the working folder
6. The application should create a new table if necessary and store the following values:
    - Event id
    - Event duration
    - Type and Host if applicable
    - Alert (true if the event took longer than 4ms, otherwise false)

## Additional points will be granted for:
1. Proper use of info and debug logging
2. Proper use of Object Oriented programming
3. Comprehensive unit test coverage
4. Efficient processing of data
5. Program that can handle very large files (gigabytes)
6. Error handling
 
