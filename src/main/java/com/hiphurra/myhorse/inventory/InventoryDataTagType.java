package com.hiphurra.myhorse.inventory;

import com.hiphurra.myhorse.MyHorse;
import net.minecraft.server.v1_16_R3.ItemStack;
import net.minecraft.server.v1_16_R3.NBTCompressedStreamTools;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.NBTTagList;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.*;
import java.math.BigInteger;

public class InventoryDataTagType implements PersistentDataType<PersistentDataContainer, InventoryData>{

    private static final UUIDTagType UUID_TAG_TYPE = new UUIDTagType();

    private final MyHorse plugin;

    public InventoryDataTagType(MyHorse plugin) {
        this.plugin = plugin;
    }

    @NotNull
    @Override
    public Class<PersistentDataContainer> getPrimitiveType() {
        return PersistentDataContainer.class;
    }

    @NotNull
    @Override
    public Class<InventoryData> getComplexType() {
        return InventoryData.class;
    }

    @NotNull
    @Override
    public PersistentDataContainer toPrimitive(@NotNull InventoryData inventoryData, @NotNull PersistentDataAdapterContext persistentDataAdapterContext) {
        PersistentDataContainer persistentDataContainer = persistentDataAdapterContext.newPersistentDataContainer();

        persistentDataContainer.set(key("items"), PersistentDataType.STRING, toBase64List(inventoryData.getItems()));
        persistentDataContainer.set(key("inventory-holder-id"), UUID_TAG_TYPE, inventoryData.getInventoryHolderId());
        return persistentDataContainer;
    }

    @NotNull
    @Override
    public InventoryData fromPrimitive(@NotNull PersistentDataContainer persistentDataContainer, @NotNull PersistentDataAdapterContext persistentDataAdapterContext) {
        return new InventoryData(
                persistentDataContainer.get(key("inventory-holder-id"), UUID_TAG_TYPE),
                fromBase64List(persistentDataContainer.get(key("items"), PersistentDataType.STRING))
        );
    }

    private NamespacedKey key(String key) {
        return new NamespacedKey(plugin, key);
    }

    /**
     * Item to Base 64
     * @param item
     * @return
     */
    public String toBase64(org.bukkit.inventory.ItemStack item) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutput = new DataOutputStream(outputStream);

        NBTTagList nbtTagListItems = new NBTTagList();
        NBTTagCompound nbtTagCompoundItem = new NBTTagCompound();

        ItemStack nmsItem = CraftItemStack.asNMSCopy(item);

        nmsItem.save(nbtTagCompoundItem);

        nbtTagListItems.add(nbtTagCompoundItem);

        NBTCompressedStreamTools.a(nbtTagCompoundItem, (DataOutput) dataOutput);

        return new BigInteger(1, outputStream.toByteArray()).toString(32);
    }

    /**
     * Item from Base64
     * @param data
     * @return
     */
    public org.bukkit.inventory.ItemStack fromBase64(String data) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(new BigInteger(data, 32).toByteArray());

        NBTTagCompound nbtTagCompoundRoot = NBTCompressedStreamTools.a((DataInput) new DataInputStream(inputStream));

        ItemStack nmsItem = ItemStack.a(nbtTagCompoundRoot);
        return CraftItemStack.asBukkitCopy(nmsItem);
    }
    /**
     * ItemStack List to Base64
     */
    public String toBase64List(org.bukkit.inventory.ItemStack[] itemStacks) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BukkitObjectOutputStream dataOutput;
        try {
            dataOutput = new BukkitObjectOutputStream(outputStream);

            // Content Size
            // Contents
            dataOutput.writeInt(itemStacks.length);

            int index = 0;
            for (org.bukkit.inventory.ItemStack itemStack : itemStacks) {
                if (itemStack != null && itemStack.getType() != Material.AIR) {
                    dataOutput.writeObject(toBase64(itemStack));
                } else {
                    dataOutput.writeObject(null);
                }
                dataOutput.writeInt(index);
                index++;
            }
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        }
        catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    /**
     * ItemStack List from Base64
     */
    public org.bukkit.inventory.ItemStack[] fromBase64List(String items) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(items));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);

            int size = dataInput.readInt();

            org.bukkit.inventory.ItemStack[] list = new org.bukkit.inventory.ItemStack[size];

            // Read the serialized inventory
            for (int i = 0; i < size; i++) {
                Object utf = dataInput.readObject();
                int slot = dataInput.readInt();
                if(utf == null) {
                    list[i] = null;
                } else{
                    list[i] = fromBase64((String) utf);
                }

            }

            dataInput.close();
            return list;
        } catch (Exception e) {
            throw new IllegalStateException("Unable to load item stacks.", e);
        }
    }
}
