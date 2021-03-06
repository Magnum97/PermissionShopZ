package nl.tabuu.permissionshopz.permissionhandler;

import nl.tabuu.permissionshopz.PermissionShopZ;
import nl.tabuu.permissionshopz.permissionhandler.exception.PermissionHandlerNotFoundException;
import org.anjocaido.groupmanager.GroupManager;
import org.anjocaido.groupmanager.Tasks.BukkitPermsUpdateTask;
import org.anjocaido.groupmanager.dataholder.OverloadedWorldHolder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class GroupManagerHandler implements IPermissionHandler {

    private GroupManager _groupManager;

    public GroupManagerHandler() {
        Plugin groupManagerPlugin = Bukkit.getPluginManager().getPlugin("GroupManager");
        if (!(groupManagerPlugin instanceof GroupManager))
            throw new PermissionHandlerNotFoundException("Could not find GroupManager.");

        _groupManager = (GroupManager) Bukkit.getServer().getPluginManager().getPlugin("GroupManager");
    }

    @Override
    public void addPermission(Player player, String permission) {
        OverloadedWorldHolder handler = _groupManager.getWorldsHolder().getWorldData(player);
        if (handler != null) {
            handler.getUser(player.getName()).addPermission(permission);
            BukkitPermsUpdateTask task = new BukkitPermsUpdateTask();
            task.run();
        } else
            PermissionShopZ.getInstance().getLogger().severe("Could not set permission of player " + player.getName());
    }
}
