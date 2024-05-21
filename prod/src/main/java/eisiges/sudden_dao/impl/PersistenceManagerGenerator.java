package eisiges.sudden_dao.impl;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import eisiges.sudden_dao.GenerateDAO;
import eisiges.sudden_dao.GenericPersistenceManager;
import jakarta.annotation.Generated;

import static eisiges.sudden_dao.impl.AnnotationProcessingUtils.*;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;

/**
 * @author kg6zvp
 */
@SupportedAnnotationTypes({
	"eisiges.sudden_dao.GenerateDAO",
	"javax.persistence.Entity"
})
@SupportedOptions({})
@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class PersistenceManagerGenerator extends AbstractProcessor {
	/**
	 * Default suffix to append to all generated DAO classes
	 */
	public static final String DAO_NAME_SUFFIX = "DAO";

	/**
	 * Annotation {@link ProcessingEnvironment}
	 */
	ProcessingEnvironment pe;

	/**
	 * Annotation processor callback
	 * @param pe {@link ProcessingEnvironment}
	 */
	@Override
	public synchronized void init(ProcessingEnvironment pe) {
		super.init(pe);
		this.pe = pe;
	}

	/**
	 * Annotation processor callback
	 * @param annotations {@link Set}
	 * @param roundEnvironment {@link RoundEnvironment}
	 * @return true or false
	 */
	@Override
	public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnvironment) {
		if (annotations.size() <= 0 || roundEnvironment.processingOver()) {
			return false;
		}
		for (Element k : roundEnvironment.getRootElements()) {
			if (!isEntity(k)) {
				continue;
			}
			// it's an entity, we good fam
			buildClass(k);
		}
		return false;
	}

	private void buildClass(Element k) {
		/*
		 * Generate Attribute Selector
		 */
		AttribSelectorGenerator attrGen = new AttribSelectorGenerator(pe);
		TypeSpec entityAttrSelector = attrGen.generateInterface(k);

		/*
		 * Generate FindBuilder
		 */
		TypeSpec finder = FindBuilderGenerator.generateBuilder(pe, k); // always generate a builder

		if (!wantsDao(k)) { // if it doesn't want a DAO, okay
			return;
		}
		
		AnnotationMirror daoAnnotation = getAnnotation(pe.getElementUtils().getAllAnnotationMirrors(k), GenerateDAO.class);
		AnnotationValue customDaoName = getAnnotationValue(daoAnnotation, "daoName");
		String daoName = customDaoName == null ? k.getSimpleName() + DAO_NAME_SUFFIX : customDaoName.getValue().toString();
		String fullyQualifiedDaoName = String.join(".", getPackageDeclaration(k), daoName);

		ClassName entityKlasse = ClassName.get(getPackageDeclaration(k), k.getSimpleName().toString());
		ClassName primaryKeyKlasse = ClassName.get(getPackageOf(getPrimaryKeyType(k)), getSimpleNameOf(getPrimaryKeyType(k)));

		MethodSpec constructor = MethodSpec.constructorBuilder()
		    .addModifiers(Modifier.PUBLIC)
		    .addStatement("super($T.class)", entityKlasse)
		    .build();

		ClassName finderCN = ClassName.get(getPackageDeclaration(k), finder.name);

		MethodSpec findMethod = MethodSpec.methodBuilder("find")
		    .addModifiers(Modifier.PUBLIC)
		    .returns(finderCN)
		    .addStatement("return new $T(em)", finderCN)
		    .build();

		AnnotationValue customParentClass = getAnnotationValue(daoAnnotation, "parentClass");
		ClassName daoParent = customParentClass == null ? ClassName.get(GenericPersistenceManager.class) : getClassNameFromFqtn(customParentClass.getValue().toString());
		
		TypeSpec.Builder daoBuilder = TypeSpec.classBuilder(daoName)
		    .superclass(ParameterizedTypeName.get(daoParent, entityKlasse, primaryKeyKlasse))
		    .addModifiers(Modifier.PUBLIC);
		
		AnnotationValue customDaoAnnotations = getAnnotationValue(daoAnnotation, "annotations");
		if(customDaoAnnotations == null) {
			daoBuilder.addAnnotation(ClassName.get("jakarta.enterprise.context", "ApplicationScoped"));
		} else {
			@SuppressWarnings("unchecked") // it will always be a list because of the API being used
			List<Object> listDaoAnnotations = (List<Object>)customDaoAnnotations.getValue();
			
			for(Object a : listDaoAnnotations){
				daoBuilder.addAnnotation(getClassNameFromFqtn(a.toString().replaceAll("\\.class$", ""))); // replace .class ending with ""
			}
		}
		
		TypeSpec daoSpec = daoBuilder
		    .addAnnotation(
				AnnotationSpec
					.builder(Generated.class)
					.addMember("value", "{\"" + PersistenceManagerGenerator.class.getName() + "\"}")
					.build())
		    .addMethod(constructor)
		    .addMethod(findMethod)
		    .build();

		writeJavaFile(pe, getPackageDeclaration(k), daoSpec);
	}
}
