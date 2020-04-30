package net.comorevi.cpapp.shop;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.network.protocol.ContainerClosePacket;
import cn.nukkit.utils.TextFormat;

public class EventListener implements Listener {
    @EventHandler
    public void onDataPacketReceive(DataPacketReceiveEvent event) {
        if (event.getPacket() instanceof ContainerClosePacket) {
            if (OfficialShopPlugin.invMap.containsKey(event.getPlayer().getName())) {
                if (OfficialShopPlugin.invMap.get(event.getPlayer().getName()).isEmpty()) {
                    OfficialShopPlugin.invMap.remove(event.getPlayer().getName());
                    event.getPlayer().sendPopup(TextFormat.GRAY + "しふぉんを閉じました。");
                    return;
                }
                FormManager.sendSellItemConfirmActivity(event.getPlayer(), OfficialShopPlugin.invMap.get(event.getPlayer().getName()));
                OfficialShopPlugin.invMap.remove(event.getPlayer().getName());
                /*
                try {
                    ApplicationManifest manifest = ApplicationData.applications.get("OfficialShop");

                    Class<? extends ActivityBase> mainClass = null;
                    mainClass = new URLClassLoader(new URL[]{new File(RuntimeData.currentDirectory + "/app/OfficialShop.jar").toURI().toURL()}, SharingData.pluginInstance.getClass().getClassLoader())
                            .loadClass("net.comorevi.cpapp.shop.sell.SellItemConfirmActivity")
                            .asSubclass(ActivityBase.class);

                    Constructor<ActivityBase> constructor = (Constructor<ActivityBase>) mainClass.getConstructor(ApplicationManifest.class);
                    ActivityBase activityBase = constructor.newInstance(manifest);

                    new SellItemConfirmActivity(manifest, OfficialShopPlugin.invMap.get(event.getPlayer().getName())).start(event.getPlayer(), activityBase.getStrings()); //TODO: 第二引数
                } catch (ClassNotFoundException | MalformedURLException | InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                    e.printStackTrace();
                } finally {
                    OfficialShopPlugin.invMap.remove(event.getPlayer().getName()); //DON't remove
                }
                */
            }
        }
    }
}
