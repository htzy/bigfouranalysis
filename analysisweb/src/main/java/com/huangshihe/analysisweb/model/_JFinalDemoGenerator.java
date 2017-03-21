package com.huangshihe.analysisweb.model;
import javax.sql.DataSource;

import com.huangshihe.analysisweb.config.MainConfig;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.druid.DruidPlugin;
/**
 * Created by huangshihe on 3/21/17.
 */
@Deprecated
public class _JFinalDemoGenerator {
    public static DataSource getDataSource() {
        PropKit.use("db_config.txt");
        DruidPlugin druidPlugin = MainConfig.createDruidPlugin();
        druidPlugin.start();
        return druidPlugin.getDataSource();
    }

    public static void main(String[] args) {
        // base model
        String baseModelPackageName = "com.huangshihe.analysisweb.model.base";

        String baseModelOutputDir = PathKit.getWebRootPath() + "/../src/com/huangshihe/analysisweb/model/base";

        String modelPackageName = "com.huangshihe.analysisweb.model";

        String modelOutputDir = baseModelOutputDir + "/..";

        Generator generator = new Generator(getDataSource(), baseModelPackageName, baseModelOutputDir, modelPackageName, modelOutputDir);

        generator.setGenerateChainSetter(false);

        generator.setGenerateDaoInModel(true);
        generator.setGenerateChainSetter(true);

        generator.setGenerateDataDictionary(false);
        generator.setRemovedTableNamePrefixes("t_");
        generator.generate();
    }
}
