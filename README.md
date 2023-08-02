# Done tasks

- **Hexagonal architecture** [#6521d6e](https://github.com/Danaes/control-api/commit/6521d6e45d129d13a4933ed95fb0aef24f7532fa)
```mermaid
  graph LR
    c1 -- primary ports ---> i1
    c1 -- secondary ports ---> o1
    o1 --> d1[(Database)]
    i1 --> HTTPS
    
    subgraph outbound
      o1((persistence))
    end
    subgraph inbound
      i1((rest))
      i2((handler))
    end
    subgraph core
        c2 --> c1
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

---
# In progress task

---
# Todo tasks

- **Testing**
  - Unit tests
  - Integration tests