package net.comorevi.cpapp.shop;

import cn.nukkit.Player;
import cn.nukkit.block.BlockChest;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;
import com.nukkitx.fakeinventories.inventory.ChestFakeInventory;
import com.nukkitx.fakeinventories.inventory.FakeSlotChangeEvent;
import net.comorevi.np.moneys.MoneySAPI;

import java.util.LinkedHashMap;
import java.util.Map;

public class OfficialShopPlugin extends PluginBase {
    protected static Map<String, Inventory> invMap = new LinkedHashMap<>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new EventListener(), this);
    }

    public static void executeSellItem(Player player, Map<Integer, Integer> itemMap) {
        MoneySAPI.getInstance().addMoney(player, calculateSellPrice(itemMap));
        player.sendPopup(TextFormat.GRAY + "アイテムを売却しました。\n売却額: " + calculateSellPrice(itemMap) + MoneySAPI.UNIT);
    }

    public static void cancelExecuteSellItem(Player player, Inventory fakeInventory) {
        fakeInventory.getContents().values().forEach(item -> {
            if (player.getInventory().canAddItem(item)) {
                player.getInventory().addItem(item);
            } else {
                player.getLevel().dropItem(player.getLocation(), item);
            }
        });
        player.sendPopup(TextFormat.GRAY + "売却をキャンセルしました。");
    }

    public static int calculateSellPrice(Map<Integer, Integer> itemMap) {
        int result = 0;
        for (int key : itemMap.keySet()) {
            result += SellItem.getById(key).getPrice() * itemMap.get(key);
        }
        return result;
    }

    public void sendSellItemInventory(Player player) {
        if (player.getLevel().getBlock(player.getLocation().up(2)) instanceof BlockChest) {
            player.sendPopup(TextFormat.GRAY + "頭上にチェストがないところに移動してください。");
            return;
        }
        ChestFakeInventory fakeInventory = new ChestFakeInventory();
        fakeInventory.setName("アイテム買取/Sell Item");
        fakeInventory.addListener(this::onSlotChange);
        player.addWindow(fakeInventory);
    }

    private void onSlotChange(FakeSlotChangeEvent event) {
        invMap.put(event.getPlayer().getName(), event.getInventory());
    }
}
