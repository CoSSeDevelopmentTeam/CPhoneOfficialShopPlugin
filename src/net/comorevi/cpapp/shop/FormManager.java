package net.comorevi.cpapp.shop;

import cn.nukkit.Player;
import cn.nukkit.inventory.Inventory;
import net.comorevi.moneyapi.MoneySAPI;
import ru.nukkitx.forms.elements.ModalForm;

import java.util.LinkedHashMap;
import java.util.Map;

public class FormManager {
    public static void sendSellItemConfirmActivity(Player player, Inventory fakeInventory) {
        Map<Integer, Integer> itemMap = new LinkedHashMap<>();
        fakeInventory.getContents().values().forEach(item -> {
            if (itemMap.containsKey(item.getId())) {
                itemMap.put(item.getId(), itemMap.get(item.getId()) + item.count);
            } else {
                itemMap.put(item.getId(), item.count);
            }
        });
        StringBuilder content = new StringBuilder();
        content.append("合計金額/Sum price: ").append(OfficialShopPlugin.calculateSellPrice(itemMap)).append(MoneySAPI.UNIT).append("\n");
        itemMap.keySet().forEach(key -> {
            content.append(" - ").append(SellItem.getById(key).getNameJpn()).append("/").append(SellItem.getById(key).getName()).append(": ").append(itemMap.get(key)).append("*").append(SellItem.getById(key).getPrice()).append(MoneySAPI.UNIT).append("\n");
        });
        getSellItemConfirmForm(content.toString()).send(player, (targetPlayer, targetForm, data) -> {
            if (targetForm.getResponse().getClickedButtonId() == 0) {
                OfficialShopPlugin.executeSellItem(targetPlayer, itemMap);
            } else {
                OfficialShopPlugin.cancelExecuteSellItem(targetPlayer, fakeInventory);
            }
        });
    }

    private static ModalForm getSellItemConfirmForm(String s) {
        ModalForm modalForm = new ModalForm()
                .setTitle("確認/Confirm - OfficialShop")
                .setContent(s)
                .setButton1("売却する/Sell items")
                .setButton2("やめる/Cancel");
        return modalForm;
    }
}
