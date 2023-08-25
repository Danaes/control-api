# Done tasks

- **Architecture** [#6521d6e](https://github.com/Danaes/control-api/commit/6521d6e45d129d13a4933ed95fb0aef24f7532fa)
```mermaid
  graph RL
  c1 <-- primary ports ---> i1
  i3 -- primary ports ---> c1
  c1 <-- secondary ports ---> o1
  c1 -- secondary ports ---> o2
  o1 <--> d1[(Database)]
  o2 --> d2[(eventBus)]
  i1 <--> HTTPS
  d2 --> i3
    
    subgraph outbound
      o1((persistence))
      o2((publisher))
    end
    subgraph inbound
      i1((rest))
      i3((listener))
    end
    subgraph core
      c2 <--> c1
        c1((application))
        c2((domain))
    end
```

- **Login system with JWT** [#6521d6e](https://github.com/Danaes/control-api/commit/6521d6e45d129d13a4933ed95fb0aef24f7532fa)
  - Add extension quarkus-smallrye-jwt
  - Create private and public key
  - Add properties to application.yaml
  - Create endpoint to sign up
  - Create endpoint to login

- **Exception handler** [#0e21cf5](https://github.com/Danaes/control-api/commit/0e21cf5fb532d2f14f3c547aa66d789a472f35a6)
  - Create custom exceptions
  - Add class with @Provider annotation

- **Authorization of endpoints** [#550cbb3](https://github.com/Danaes/control-api/commit/550cbb3ea1fd368845f237faa3c627e5ca708561)
  - Add @RolesAllowed annotation

- **Testing** [#7813f64](https://github.com/Danaes/control-api/commit/7813f640280b56593885a9de57bbcfb03cd572b5)
  - Unit tests
  - Integration tests

- **Event bus** [#3dde708](https://github.com/Danaes/control-api/commit/3dde708283331dd600d827b7f4b7f485914e02f7)
  - Create event
  - Send event
  - Receive event
---
# In progress task


---
# Todo tasks