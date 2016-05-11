package ch.quantasy.tinkerforge.factory;

import ch.quantasy.tinkerforge.stack.TinkerforgeStack;
import ch.quantasy.tinkerforge.stack.TinkerforgeStackAddress;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class TinkerforgeStackFactory {

    private static TinkerforgeStackFactory instance;
    private final HashMap<TinkerforgeStackAddress, TinkerforgeStack> tinkerforgeStacks;
    
    static {
        instance = new TinkerforgeStackFactory();
    }

    private TinkerforgeStackFactory() {
        this.tinkerforgeStacks = new HashMap<>();
       
    }

    public static TinkerforgeStackFactory getInstance() {
        return TinkerforgeStackFactory.instance;
    }

    

    public boolean addStack(final TinkerforgeStackAddress address) {
        if (this.tinkerforgeStacks.containsKey(this)) {
            return false;
        }
        this.tinkerforgeStacks.put(address, new TinkerforgeStack(address));
        return true;
    }

    public TinkerforgeStack getStack(final TinkerforgeStackAddress address) {
        return this.tinkerforgeStacks.get(address);
    }

    public TinkerforgeStack removeStack(final TinkerforgeStackAddress address) {
        if (!this.tinkerforgeStacks.containsKey(address)) {
            return null;
        }
        TinkerforgeStack stack = this.tinkerforgeStacks.remove(address);
        return stack;
    }

    public boolean containsStack(final TinkerforgeStackAddress address) {
        return (this.tinkerforgeStacks.containsKey(address));
    }

    public Set<TinkerforgeStackAddress> getTinkerforgeStackAddress() {
        return new HashSet<>(this.tinkerforgeStacks.keySet());
    }

    public Set<TinkerforgeStack> getTinkerforgeStacks() {
        return new HashSet<>(this.tinkerforgeStacks.values());
    }

}
