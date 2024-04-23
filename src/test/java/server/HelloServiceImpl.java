package server;

import rpc.tomstillcoding.com.IDL.Hello.HelloRequest;
import rpc.tomstillcoding.com.IDL.Hello.HelloResponse;
import rpc.tomstillcoding.com.IDL.Hello.HelloService;

import java.util.HashMap;
import java.util.Map;

public class HelloServiceImpl implements HelloService {
    // 存储卡号和金额的哈希表
    private Map<String, Integer> cardMap = new HashMap<>();

    @Override
    public HelloResponse hello(HelloRequest request) {
        String name = request.getName();
        String retMsg = "hello: " + name;
        HelloResponse response = new HelloResponse(retMsg);
        return response;
    }

    @Override
    public HelloResponse hi(HelloRequest request) {
        String name = request.getName();
        String retMsg = "hi: " + name;
        HelloResponse response = new HelloResponse(retMsg);
        return response;
    }

    @Override
    public HelloResponse msg(HelloRequest request) {
        // 获取请求中的卡号和金额
        String card = request.getCard();
        Integer existingMoney = cardMap.get(card);
        int money = request.getMoney();
        if (existingMoney != null) {
            money = request.getMoney() + existingMoney;
            // 将卡号和金额存储在哈希表中
            cardMap.put(card, money);

            String retMsg = "卡号: " + card + "，金额为: " + money;
            HelloResponse response = new HelloResponse(retMsg);
            return response;
        } else {
            // 如果卡号不存在，将新金额添加到哈希表
            cardMap.put(card, money);
            String retMsg = "卡号: " + card + "，金额已设置为: " + money;
            return new HelloResponse(retMsg);
        }
    }

    // 额外的方法，获取指定卡号的金额
    public HelloResponse getCardMoney(HelloRequest request) {
        String card = request.getCard();

        // 如果哈希表中包含这个卡号，返回对应的金额
        if (cardMap.containsKey(card)) {
            int money = cardMap.get(card);
            return new HelloResponse("卡号为 " + card + " 的金额是 " + money);
        } else {
            return new HelloResponse("卡号 " + card + " 不存在");
        }
    }
}
