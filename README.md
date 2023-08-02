# Done tasks
- **Hexagonal architecture**
  - common -> shared package between bounded contexts
    - core.application
  - layers
    - inbound
      - rest
      - exception.handler
    - outbound
      - persistence
    - core
      - application
      - domain

```mermaid
  graph LR;
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

- **Login system with JWT**
  - Add extension quarkus-smallrye-jwt
  - Create private and public key
  - Add properties to application.yaml
  - Create endpoint to sign up
  - Create endpoint to login

- **Exception handler**
  - Create custom exceptions
  - Add class with @Provider annotation

---
# In progress task

- **Authorization of endpoints**
  - Add @RolesAllowed annotation

---
# Todo tasks

- **Testing**
  - Unit tests
  - Integration tests