package tsml.classifiers.distance_based.knn.configs;

import com.google.common.collect.ImmutableList;
import evaluation.storage.ClassifierResults;
import tsml.classifiers.distance_based.utils.classifier_building.ClassifierBuilderFactory;
import tsml.classifiers.distance_based.utils.classifier_building.ClassifierBuilderFactory.ClassifierBuilder;
import tsml.classifiers.distance_based.utils.classifier_building.ClassifierBuilderFactory.Tag;
import experiments.data.DatasetLoading;
import tsml.classifiers.distance_based.tuned.RLTunedClassifier;
import tsml.classifiers.distance_based.distances.DistanceMeasureConfigs;
import tsml.classifiers.distance_based.distances.ddtw.DDTWDistance;
import tsml.classifiers.distance_based.distances.dtw.DTWDistance;
import tsml.classifiers.distance_based.knn.KNNLOOCV;
import tsml.classifiers.distance_based.knn.neighbour_iteration.LinearNeighbourIteratorBuilder;
import tsml.classifiers.distance_based.knn.neighbour_iteration.RandomNeighbourIteratorBuilder;
import utilities.ClassifierTools;
import tsml.classifiers.distance_based.utils.iteration.LinearListIterator;
import tsml.classifiers.distance_based.utils.iteration.RandomListIterator;
import tsml.classifiers.distance_based.utils.params.ParamSpace;
import weka.classifiers.Classifier;
import weka.core.Instances;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;


public enum KNNConfig implements ClassifierBuilder {

    ED_1NN_V1(KNNConfig::buildEd1nnV1, KnnTag.DISTANCE, KnnTag.UNIVARIATE, KnnTag.SIMILARITY),
    DTW_1NN_V1(KNNConfig::buildDtw1nnV1, KnnTag.DISTANCE, KnnTag.UNIVARIATE, KnnTag.SIMILARITY),
    DDTW_1NN_V1(KNNConfig::buildDdtw1nnV1, KnnTag.DISTANCE, KnnTag.UNIVARIATE, KnnTag.SIMILARITY),
    TUNED_DTW_1NN_V1(KNNConfig::buildTunedDtw1nnV1, KnnTag.DISTANCE, KnnTag.UNIVARIATE, KnnTag.SIMILARITY),
    TUNED_DDTW_1NN_V1(KNNConfig::buildTunedDdtw1nnV1, KnnTag.DISTANCE, KnnTag.UNIVARIATE, KnnTag.SIMILARITY),
    TUNED_WDTW_1NN_V1(KNNConfig::buildTunedWdtw1nnV1, KnnTag.DISTANCE, KnnTag.UNIVARIATE, KnnTag.SIMILARITY),
    TUNED_WDDTW_1NN_V1(KNNConfig::buildTunedWddtw1nnV1, KnnTag.DISTANCE, KnnTag.UNIVARIATE, KnnTag.SIMILARITY),
    TUNED_ERP_1NN_V1(KNNConfig::buildTunedErp1nnV1, KnnTag.DISTANCE, KnnTag.UNIVARIATE, KnnTag.SIMILARITY),
    TUNED_MSM_1NN_V1(KNNConfig::buildTunedMsm1nnV1, KnnTag.DISTANCE, KnnTag.UNIVARIATE, KnnTag.SIMILARITY),
    TUNED_LCSS_1NN_V1(KNNConfig::buildTunedLcss1nnV1, KnnTag.DISTANCE, KnnTag.UNIVARIATE, KnnTag.SIMILARITY),
    TUNED_TWED_1NN_V1(KNNConfig::buildTunedTwed1nnV1, KnnTag.DISTANCE, KnnTag.UNIVARIATE, KnnTag.SIMILARITY),

    ED_1NN_V2(KNNConfig::buildEd1nnV2, KnnTag.DISTANCE, KnnTag.UNIVARIATE, KnnTag.SIMILARITY),
    DTW_1NN_V2(KNNConfig::buildDtw1nnV2, KnnTag.DISTANCE, KnnTag.UNIVARIATE, KnnTag.SIMILARITY),
    DDTW_1NN_V2(KNNConfig::buildDdtw1nnV2, KnnTag.DISTANCE, KnnTag.UNIVARIATE, KnnTag.SIMILARITY),
    TUNED_DTW_1NN_V2(KNNConfig::buildTunedDtw1nnV2, KnnTag.DISTANCE, KnnTag.UNIVARIATE, KnnTag.SIMILARITY),
    TUNED_DDTW_1NN_V2(KNNConfig::buildTunedDdtw1nnV2, KnnTag.DISTANCE, KnnTag.UNIVARIATE, KnnTag.SIMILARITY),
    TUNED_WDTW_1NN_V2(KNNConfig::buildTunedWdtw1nnV2, KnnTag.DISTANCE, KnnTag.UNIVARIATE, KnnTag.SIMILARITY),
    TUNED_WDDTW_1NN_V2(KNNConfig::buildTunedWddtw1nnV2, KnnTag.DISTANCE, KnnTag.UNIVARIATE, KnnTag.SIMILARITY),
    TUNED_ERP_1NN_V2(KNNConfig::buildTunedErp1nnV2, KnnTag.DISTANCE, KnnTag.UNIVARIATE, KnnTag.SIMILARITY),
    TUNED_MSM_1NN_V2(KNNConfig::buildTunedMsm1nnV2, KnnTag.DISTANCE, KnnTag.UNIVARIATE, KnnTag.SIMILARITY),
    TUNED_LCSS_1NN_V2(KNNConfig::buildTunedLcss1nnV2, KnnTag.DISTANCE, KnnTag.UNIVARIATE, KnnTag.SIMILARITY),
    TUNED_TWED_1NN_V2(KNNConfig::buildTunedTwed1nnV2, KnnTag.DISTANCE, KnnTag.UNIVARIATE, KnnTag.SIMILARITY),

    ;

    public String toString() {
        return classifierBuilder.toString();
    }

    @Override
    public String getName() {
        return classifierBuilder.getName();
    }

    @Override
    public Classifier build() {
        return classifierBuilder.build();
    }

    @Override
    public ImmutableList<? extends Tag> getTags() {
        return classifierBuilder.getTags();
    }

    private final ClassifierBuilder classifierBuilder;

    KNNConfig(Supplier<? extends Classifier> supplier, Tag... tags) {
        classifierBuilder = new ClassifierBuilderFactory.SuppliedClassifierBuilder(name(), supplier, tags);
    }

    public static List<ClassifierBuilder> all() {
        return Arrays.stream(values()).map(i -> (ClassifierBuilder) i).collect(Collectors.toList());
    }
    
    public static KNNLOOCV build1nnV1() {
        KNNLOOCV classifier = new KNNLOOCV();
        classifier.setEarlyAbandon(true);
        classifier.setK(1);
        classifier.setNeighbourLimit(-1);
        classifier.setNeighbourIteratorBuilder(new LinearNeighbourIteratorBuilder(classifier));
//        classifier.setRandomTieBreak(false);
        return classifier;
    }

    public static KNNLOOCV build1nnV2() {
        KNNLOOCV classifier = new KNNLOOCV();
        classifier.setEarlyAbandon(true);
        classifier.setK(1);
        classifier.setNeighbourLimit(-1);
        classifier.setNeighbourIteratorBuilder(new RandomNeighbourIteratorBuilder(classifier));
        classifier.setCvSearcherIteratorBuilder(new RandomNeighbourIteratorBuilder(classifier));
//        classifier.setRandomTieBreak(true);
        return classifier;
    }

    public static KNNLOOCV buildEd1nnV1() {
        KNNLOOCV knn = build1nnV1();
        knn.setDistanceFunction(new DTWDistance(0));
        return knn;
    }

    public static KNNLOOCV buildDtw1nnV1() {
        KNNLOOCV knn = build1nnV1();
        knn.setDistanceFunction(new DTWDistance(-1));
        return knn;
    }

    public static KNNLOOCV buildDdtw1nnV1() {
        KNNLOOCV knn = build1nnV1();
        knn.setDistanceFunction(new DDTWDistance(-1));
        return knn;
    }

    public static KNNLOOCV buildEd1nnV2() {
        KNNLOOCV knn = build1nnV2();
        knn.setDistanceFunction(new DTWDistance(0));
        return knn;
    }

    public static KNNLOOCV buildDtw1nnV2() {
        KNNLOOCV knn = build1nnV2();
        knn.setDistanceFunction(new DTWDistance(-1));
        return knn;
    }

    public static KNNLOOCV buildDdtw1nnV2() {
        KNNLOOCV knn = build1nnV2();
        knn.setDistanceFunction(new DDTWDistance(-1));
        return knn;
    }


    public static void main(String[] args) throws Exception {
        int seed = 0;
        Instances[] data = DatasetLoading.sampleGunPoint(seed);
        RLTunedClassifier classifier = buildTunedDtw1nnV1();
        classifier.setSeed(seed); // set seed
        classifier.setEstimateOwnPerformance(true);
        ClassifierResults results = ClassifierTools.trainAndTest(data, classifier);
        results.setDetails(classifier, data[1]);
        ClassifierResults trainResults = classifier.getTrainResults();
        trainResults.setDetails(classifier, data[0]);
        System.out.println(trainResults.writeSummaryResultsToString());
        System.out.println(results.writeSummaryResultsToString());
    }

    public static RLTunedClassifier buildTunedDtw1nnV1() {
        return buildTuned1nnV1(DistanceMeasureConfigs::buildDtwSpaceV1);
    }

    public static RLTunedClassifier buildTunedDdtw1nnV1() {
        return buildTuned1nnV1(DistanceMeasureConfigs::buildDdtwSpaceV1);
    }

    public static RLTunedClassifier buildTunedWdtw1nnV1() {
        return buildTuned1nnV1(i -> DistanceMeasureConfigs.buildWdtwSpaceV1());
    }

    public static RLTunedClassifier buildTunedWddtw1nnV1() {
        return buildTuned1nnV1(i -> DistanceMeasureConfigs.buildWddtwSpaceV1());
    }

    public static RLTunedClassifier buildTunedDtw1nnV2() {
        return buildTuned1nnV2(DistanceMeasureConfigs::buildDtwSpaceV2);
    }

    public static RLTunedClassifier buildTunedDdtw1nnV2() {
        return buildTuned1nnV2(DistanceMeasureConfigs::buildDdtwSpaceV2);
    }

    public static RLTunedClassifier buildTunedWdtw1nnV2() {
        return buildTuned1nnV1(i -> DistanceMeasureConfigs.buildWdtwSpaceV2());
    }

    public static RLTunedClassifier buildTunedWddtw1nnV2() {
        return buildTuned1nnV1(i -> DistanceMeasureConfigs.buildWddtwSpaceV2());
    }

    public static RLTunedClassifier buildTunedMsm1nnV1() {
        return buildTuned1nnV1(i -> DistanceMeasureConfigs.buildMsmSpace());
    }

    public static RLTunedClassifier buildTunedTwed1nnV1() {
        return buildTuned1nnV1(i -> DistanceMeasureConfigs.buildTwedSpace());
    }

    public static RLTunedClassifier buildTunedErp1nnV1() {
        return buildTuned1nnV1(DistanceMeasureConfigs::buildErpSpace);
    }

    public static RLTunedClassifier buildTunedLcss1nnV1() {
        return buildTuned1nnV1(DistanceMeasureConfigs::buildLcssSpace);
    }

    public static RLTunedClassifier buildTunedMsm1nnV2() {
        return buildTuned1nnV2(i -> DistanceMeasureConfigs.buildMsmSpace());
    }

    public static RLTunedClassifier buildTunedTwed1nnV2() {
        return buildTuned1nnV2(i -> DistanceMeasureConfigs.buildTwedSpace());
    }

    public static RLTunedClassifier buildTunedErp1nnV2() {
        return buildTuned1nnV2(DistanceMeasureConfigs::buildErpSpace);
    }

    public static RLTunedClassifier buildTunedLcss1nnV2() {
        return buildTuned1nnV2(DistanceMeasureConfigs::buildLcssSpace);
    }


    public static RLTunedClassifier buildTuned1nnV1(Function<Instances, ParamSpace> paramSpaceFunction) {
        RLTunedClassifier incTunedClassifier = new RLTunedClassifier();
        RLTunedKNNSetup RLTunedKNNSetup = new RLTunedKNNSetup();
        RLTunedKNNSetup
                .setIncTunedClassifier(incTunedClassifier)
                .setParamSpace(paramSpaceFunction)
                .setKnnSupplier(KNNConfig::build1nnV1).setImproveableBenchmarkIteratorBuilder(LinearListIterator::new);
        incTunedClassifier.setTrainSetupFunction(RLTunedKNNSetup);
        return incTunedClassifier;
    }

    public static RLTunedClassifier buildTuned1nnV1(ParamSpace paramSpace) {
        return buildTuned1nnV1(i -> paramSpace);
    }

    public static RLTunedClassifier buildTuned1nnV2(Function<Instances, ParamSpace> paramSpaceFunction) {
        RLTunedClassifier incTunedClassifier = new RLTunedClassifier();
        RLTunedKNNSetup RLTunedKNNSetup = new RLTunedKNNSetup();
        RLTunedKNNSetup
                .setIncTunedClassifier(incTunedClassifier)
                .setParamSpace(paramSpaceFunction)
                .setKnnSupplier(KNNConfig::build1nnV1).setImproveableBenchmarkIteratorBuilder(benchmarks -> new RandomListIterator<>(benchmarks, incTunedClassifier.getSeed()));
        incTunedClassifier.setTrainSetupFunction(RLTunedKNNSetup);
        return incTunedClassifier;
    }

    public static RLTunedClassifier buildTuned1nnV2(ParamSpace paramSpace) {
        return buildTuned1nnV2(i -> paramSpace);
    }
}