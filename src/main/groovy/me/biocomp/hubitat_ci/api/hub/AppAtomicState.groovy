package me.biocomp.hubitat_ci.api.hub

trait AppAtomicState {
    abstract void clear()
    abstract java.lang.Object compute(java.lang.Object a, java.util.function.BiFunction b)
    abstract java.lang.Object computeIfAbsent(java.lang.Object a, java.util.function.Function b)
    abstract java.lang.Object computeIfPresent(java.lang.Object a, java.util.function.BiFunction b)
    abstract boolean containsKey(java.lang.Object a)
    abstract boolean containsValue(java.lang.Object a)
    abstract java.util.Set entrySet()
    abstract void forEach(java.util.function.BiConsumer a)
    abstract java.lang.Object get(java.lang.Object a)
    abstract java.lang.Object getOrDefault(java.lang.Object a, java.lang.Object b)
    abstract boolean isEmpty()
    abstract java.util.Set keySet()
    abstract void loadState()
    abstract java.lang.Object merge(java.lang.Object a, java.lang.Object b, java.util.function.BiFunction c)
    abstract java.lang.Object put(java.lang.Object a, java.lang.Object b)
    abstract void putAll(java.util.Map a)
    abstract java.lang.Object putIfAbsent(java.lang.Object a, java.lang.Object b)
    abstract java.lang.Object remove(java.lang.Object a)
    abstract boolean remove(java.lang.Object a, java.lang.Object b)
    abstract java.lang.Object replace(java.lang.Object a, java.lang.Object b)
    abstract boolean replace(java.lang.Object a, java.lang.Object b, java.lang.Object c)
    abstract void replaceAll(java.util.function.BiFunction a)
    abstract int size()
    abstract java.util.Collection values()
}
