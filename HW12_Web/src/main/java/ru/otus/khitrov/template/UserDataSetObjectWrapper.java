package ru.otus.khitrov.template;

import freemarker.template.*;
import ru.otus.khitrov.base.dataSets.UserDataSet;

public class UserDataSetObjectWrapper extends DefaultObjectWrapper {

    public UserDataSetObjectWrapper(Version incompatibleImprovements) {
        super(incompatibleImprovements);
    }

    @Override
    protected TemplateModel handleUnknownType(final Object obj) throws TemplateModelException {
        if (obj instanceof UserDataSet) {
                       return new AdapterTemplateModel() {
                           @Override
                           public Object getAdaptedObject(Class<?> aClass) {


                               return null;

                           }
                       };

        }

        return super.handleUnknownType(obj);
    }






}
