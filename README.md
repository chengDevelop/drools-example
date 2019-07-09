[中文介绍](https://github.com/huahuayu/drools/blob/master/README_CN.md)
## Introduction 
A spring boot pricing service by use drools engine

## Function
1. discount by customer type  

| customer  | discount  |
|---|---|
| ordinary customer  | 10% off  |
| vip  | 20% off  |
| advanced vip  | 30% off  |


2. based on customer type discount, calculate reduction by payment method 

| payment method  | reduction  |
|---|---|
| creditCard  | no reduction  |
| wepay  | $5 reduction when total amount > $100  |
| alipay  | $10 reduction when total amount > $100  |

## Drools version
7.23.0.Final  


## QuickStart
### clone repo
git clone https://github.com/huahuayu/drools.git

### import project
open project in Intellij idea or Eclipse, wait for a moment to download dependencies.  

### run test
[DroolsTest.java](https://github.com/huahuayu/drools/blob/master/src/test/java/com/huahuayu/drools/DroolsTest.java)  
``` java
public class DroolsTest {

    @Autowired
    private PricingService pricingService;


    @Test
    public void getPriceTest() {
        // alice, vip， buy iphoneXR，price $1000, use wepay
        Order order1 = new Order(1,new Customer("alice","2"),new Product("iphoneXR",1000.00f), new Payment("wepay"));
        // bob, ordinary customer，buy macbook pro，price $2000, use credit card
        Order order2 = new Order(2,new Customer("bob","1"),new Product("macbook pro",2000.00f), new Payment("creditCard"));
        // eva, advanced vip，buy mouse，price $99, use alipay
        Order order3 = new Order(3,new Customer("eva","3"),new Product("mouse",99.00f), new Payment("alipay"));
        // frank, advanced vip, buy airpod, price $200, use alipay
        Order order4 = new Order(4,new Customer("frank","3"),new Product("airpod",200.00f), new Payment("alipay"));

        Result result1 = pricingService.getTheResult(order1);
        Result result2 = pricingService.getTheResult(order2);
        Result result3 = pricingService.getTheResult(order3);
        Result result4 = pricingService.getTheResult(order4);

        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
        System.out.println(result4);

        assertEquals("order1 finalPrice is wrong",new Float(1000.00f * 0.8 - 5),result1.getFinalPrice());
        assertEquals("order2 finalPrice is wrong",new Float(2000.00f * 0.9 - 0),result1.getFinalPrice());
        assertEquals("order3 finalPrice is wrong",new Float(99.00f * 0.7 - 0),result1.getFinalPrice());
        assertEquals("order4 finalPrice is wrong",new Float(200.00f * 0.7 - 10),result1.getFinalPrice());

    }

}
```

**Result**    
```
Result(order=Order(orderId=1, customer=Customer(name=alice, type=2), product=Product(name=iphoneXR, price=1000.0), payment=Payment(name=wepay)), discount=0.8, reduction=5.0, finalPrice=995.0)
Result(order=Order(orderId=2, customer=Customer(name=bob, type=1), product=Product(name=macbook pro, price=2000.0), payment=Payment(name=creditCard)), discount=0.9, reduction=null, finalPrice=1800.0)
Result(order=Order(orderId=3, customer=Customer(name=eva, type=3), product=Product(name=mouse, price=99.0), payment=Payment(name=alipay)), discount=0.7, reduction=null, finalPrice=69.299995)
Result(order=Order(orderId=3, customer=Customer(name=frank, type=4), product=Product(name=airpod, price=200.0), payment=Payment(name=alipay)), discount=null, reduction=10.0, finalPrice=190.0)
```


## Future plan
pricing by more condition combination, like:  
1. product category
1. production attribution(proprietary or third party)
1. delivery address(determine the ship price)
1. commodity combination sales 
1. holiday campaign in limited date


