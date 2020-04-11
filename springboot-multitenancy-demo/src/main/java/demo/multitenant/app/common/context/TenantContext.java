package demo.multitenant.app.common.context;

public class TenantContext {

    private static ThreadLocal<String> context = new InheritableThreadLocal<>();

    public static void setCurrentTenant(String tenantId) {
        context.set(tenantId);
    }

    public static String getCurrentTenant() {
        return context.get();
    }

    public static void clear() {
        context.remove();
    }
}
