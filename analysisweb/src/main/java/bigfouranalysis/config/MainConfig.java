package bigfouranalysis.config;

import bigfouranalysis.controller.MainController;
import com.jfinal.config.*;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;

/**
 * Created by root on 3/21/17.
 */
public class MainConfig extends JFinalConfig {
    @Override
    public void configConstant(Constants constants) {
        PropKit.use("db_config.txt");
        constants.setDevMode(PropKit.getBoolean("devMode", false));
        constants.setViewType(ViewType.JSP);
    }

    @Override
    public void configRoute(Routes routes) {
        routes.setBaseViewPath("/WEB-INF/views");
        routes.add("/", MainController.class);

    }

    @Override
    public void configEngine(Engine engine) {

    }

    @Override
    public void configPlugin(Plugins plugins) {
        // C3p0
        DruidPlugin druidPlugin = createDruidPlugin();
        plugins.add(druidPlugin);

        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
        plugins.add(arp);
    }

    @Override
    public void configInterceptor(Interceptors interceptors) {

    }

    @Override
    public void configHandler(Handlers handlers) {

    }

    public static DruidPlugin createDruidPlugin(){
        return new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
    }
}
