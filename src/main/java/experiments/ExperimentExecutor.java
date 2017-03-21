/**
 * Created by Valerii Pozdiaev on 2017.
 */
package experiments;

import java.util.ArrayList;
import java.util.List;

public class ExperimentExecutor {
    // TODO
    // Параметры задачи: размер сети, размер хранилища
    // Изменяемые характеристики алгоритма:
    // Размер популяции, параметры кроссовера, мутатора и селектора
    // Параметры для исследования: время работы, значение фитнесс-функции у лучшей особи

    List<AbstractExperiment> experiments;

    public ExperimentExecutor() {
        experiments = new ArrayList<>();
    }

    public void addExperiment(AbstractExperiment experiment) {
        experiments.add(experiment);
    }

    public void process() {
        for (AbstractExperiment experiment : experiments) {
            experiment.execute();
        }
    }
}
