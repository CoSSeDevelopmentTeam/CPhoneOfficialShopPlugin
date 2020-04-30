package net.comorevi.cpapp.shop;

import cn.nukkit.block.Block;

import java.util.LinkedList;
import java.util.List;

public enum SellItem {
    STONE(Block.STONE, 0, "Stone", "焼き石", 5),
    GRASS(Block.GRASS, 0, "Grass", "草", 5),
    DIRT(Block.DIRT, 0, "Dirt", "土", 2),
    COBBLE(Block.COBBLE, 0, "Cobble Stone", "丸石", 2),
    UNKNOWN(-1, 0, "Unknown", "未設定", 1);

    private final int id;
    private final int meta;
    private final String name;
    private final String nameJpn;
    private final int price;

    private SellItem(int id, int meta, String name, String nameJpn, int price) {
        this.id = id;
        this.meta = meta;
        this.name = name;
        this.nameJpn = nameJpn;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public int getMeta() {
        return meta;
    }

    public String getName() {
        return name;
    }

    public String getNameJpn() {
        return nameJpn;
    }

    public int getPrice() {
        return price;
    }

    public static SellItem getById(int id) {
        for (SellItem type : SellItem.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return UNKNOWN;
    }

    public static SellItem getByName(String name) {
        for (SellItem type : SellItem.values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        return UNKNOWN;
    }

    public static SellItem getByNameJpn(String nameJpn) {
        for (SellItem type : SellItem.values()) {
            if (type.getNameJpn().equals(nameJpn)) {
                return type;
            }
        }
        return UNKNOWN;
    }

    public static List<SellItem> getShopItemList() {
        List<SellItem> list = new LinkedList<>();
        for (SellItem item : SellItem.values()) {
            list.add(item);
        }
        return list;
    }

    @Override
    public String toString() {
        return "ShopItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nameJpn='" + nameJpn +'\'' +
                '}';
    }
}
