package ru.moysklad.remap_1_2.entities.permissions;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.Meta;
import ru.moysklad.remap_1_2.entities.MetaEntity;

@Getter
@Setter
@EqualsAndHashCode
public class EmployeeRole extends MetaEntity {
    private EmployeePermissions permissions;

    /**
     * Для инициализации объекта используйте статичные фабричные методы: <code>adminRole</code>, <code>cashierRole</code>, <code>workerRole</code>, <code>individualRole</code>. <br>
     * В противном случае для post/put запросов необходимо самостоятельно заполнить объект Meta
     */
    @Deprecated
    public EmployeeRole() {
    }

    public static EmployeeRole adminRole() {
        return new AdminRole();
    }

    public static EmployeeRole cashierRole() {
        return new CashierRole();
    }

    public static EmployeeRole workerRole() {
        return new WorkerRole();
    }

    public static EmployeeRole individualRole() {
        return new IndividualRole();
    }

    public interface DefaultRole {
        String name();
        Meta.Type type();
    }

    private static class AdminRole extends EmployeeRole implements DefaultRole {
        @Override
        public String name() {
            return "admin";
        }
        @Override
        public Meta.Type type() {
            return Meta.Type.SYSTEM_ROLE;
        }
    }

    private static class CashierRole extends EmployeeRole implements DefaultRole {
        @Override
        public String name() {
            return "cashier";
        }
        @Override
        public Meta.Type type() {
            return Meta.Type.SYSTEM_ROLE;
        }
    }

    private static class WorkerRole extends EmployeeRole implements DefaultRole {
        @Override
        public String name() {
            return "worker";
        }
        @Override
        public Meta.Type type() {
            return Meta.Type.SYSTEM_ROLE;
        }
    }

    private static class IndividualRole extends EmployeeRole implements DefaultRole {
        @Override
        public String name() {
            return "individual";
        }
        @Override
        public Meta.Type type() {
            return Meta.Type.INDIVIDUAL_ROLE;
        }
    }
}
