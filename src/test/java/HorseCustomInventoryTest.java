import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.entity.EntityMock;
import be.seeseemelk.mockbukkit.entity.LivingEntityMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.hiphurra.myhorse.MyHorse;
import com.hiphurra.myhorse.inventory.InventoryManager;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;

public class HorseCustomInventoryTest {

    private ServerMock server;
    private MyHorsePlugin plugin;

    @BeforeEach
    public void setUp() {
        // Start the mock server
        server = MockBukkit.mock();
        // Load your plugin
        plugin = MockBukkit.load(MyHorsePlugin.class);
    }

    @AfterEach
    public void tearDown() {
        // Stop the mock server
        MockBukkit.unmock();
    }

    @Test
    public void thisTestWillSucceed() {
        WorldMock world = new WorldMock();
        server.addWorld(world);

        PlayerMock player = server.addPlayer("testy");
        player.setOp(true);

        Location location = new Location(world, 0, 0, 0);
        AbstractHorse horse = world.spawn(location, Horse.class);

        horse.addPassenger(player);
        assertTrue(horse.getPassengers().contains(player));
    }
}
