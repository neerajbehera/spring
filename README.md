
# create  a sample order
## create a order and save order items in another table via 1:M relation
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": 123,
    "items": [
      {
        "productId": "prod-1002",
        "quantity": 3,
        "unitPrice": 21
      }
    ]
  }'

# check all exposed endpoints
 @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("\n===== REGISTERED ENDPOINTS =====");
        requestMappingHandlerMapping.getHandlerMethods().forEach((key, value) -> {
            System.out.println(key + " -> " + value);
        });
        System.out.println("==============================\n");
    }

# bean not found error
@SpringBootApplication(scanBasePackages = {"com.example.ecommerce.demo.controller","service","model", "repository","model","dto"})


# login to h2 
## url -> jdbc:h2:~/testdb
## username -> sa
## password -> leave blank
![Alt text](image.png)