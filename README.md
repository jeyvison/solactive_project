#Solactive project

#### Instructions to run the project 
This is a simple Spring Boot project so you can run
```
./mvnw spring-boot:run
```

to start the project or 

```
./mvnw test
```

to run the tests.

### Assumptions

- I'm recalculating the 60 seconds window at every addition of a Tick
but i'm also assuming that the API might not receive some Tick for more than one second,
so that's why i created a Timer that will update the value at every second.
- I know the Timer Task is single threaded but (being optimistic here)
i don't expect the calculation to take more than one second, so delays will be avoided
- I thought about using a ConcurrentHashMap for all the Ticks that arrive but that could
block te execution for every thread that is coming



### Conclusions
This Challenge was awesome. It helped me to think again about my multi-threading and 
streaming skills. I hope you like it. 
