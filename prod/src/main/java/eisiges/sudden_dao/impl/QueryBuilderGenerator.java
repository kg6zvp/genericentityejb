package eisiges.sudden_dao.impl;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;

import static eisiges.sudden_dao.impl.AnnotationProcessingUtils.*;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

/**
 * Annotation processor for generating a QueryBuilder
 * @author kg6zvp
 */
public class QueryBuilderGenerator {
	/**
	 * factory method for generating the query builder code
	 * @param pe {@link ProcessingEnvironment}
	 * @param k {@link Element}
	 * @return {@link TypeSpec}
	 */
	public static TypeSpec generateBuilder(ProcessingEnvironment pe, Element k) {
		String finderName = k.getSimpleName() + "QueryBuilder";
		
		ClassName entityKlasse = ClassName.get(getPackageDeclaration(k), k.getSimpleName().toString());

		MethodSpec whereMethod = MethodSpec.methodBuilder("where")
			.addStatement("query.append($S)", " WHERE")
			//.returns(null)
			.build();

		return null;
	}
}