import java.io.BufferedReader;
import java.io.FileReader;

import weka.core.Instances;
import weka.associations.Apriori;
import weka.associations.AssociationRules;
import weka.associations.FPGrowth;

public class weka {

	public static void main(String args[]) throws Exception {

		// load data
		Instances data = new Instances(new BufferedReader(new FileReader("D:\\bai2.arff")));

		// build model
		Apriori model = new Apriori();
		// FPGrowth gr = new FPGrowth();
		model.setLowerBoundMinSupport(0.3);
		model.setMinMetric(0.7);
		model.buildAssociations(data);
		AssociationRules rules = model.getAssociationRules();
		for (int i = 0; i < rules.getRules().size(); i++) {
			System.out.println(rules.getRules().get(i) + "rulessss");
		}

		// System.out.println(model);

	}
}