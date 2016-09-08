/*
 *
 *  * Copyright 2015-2016 the original author or authors.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */
package com.yqboots.project.fss.web.form;

import com.yqboots.project.web.support.WebKeys;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

/**
 * Validates the FileUploadForm.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
public class FileUploadFormValidator implements Validator {
    /**
     * Can this {@link org.springframework.validation.Validator} {@link #validate(Object, org.springframework.validation.Errors) validate}
     * instances of the supplied {@code clazz}?
     * <p>This method is <i>typically</i> implemented like so:
     * <pre class="code">return Foo.class.isAssignableFrom(clazz);</pre>
     * (Where {@code Foo} is the class (or superclass) of the actual
     * object instance that is to be {@link #validate(Object, org.springframework.validation.Errors) validated}.)
     *
     * @param clazz the {@link Class} that this {@link org.springframework.validation.Validator} is
     *              being asked if it can {@link #validate(Object, org.springframework.validation.Errors) validate}
     * @return {@code true} if this {@link org.springframework.validation.Validator} can indeed
     * {@link #validate(Object, org.springframework.validation.Errors) validate} instances of the
     * supplied {@code clazz}
     */
    @Override
    public boolean supports(final Class<?> clazz) {
        return FileUploadForm.class.equals(clazz);
    }

    /**
     * Validate the supplied {@code target} object, which must be
     * of a {@link Class} for which the {@link #supports(Class)} method
     * typically has (or would) return {@code true}.
     * <p>The supplied {@link org.springframework.validation.Errors errors} instance can be used to report
     * any resulting validation errors.
     *
     * @param target the object that is to be validated (can be {@code null})
     * @param errors contextual state about the validation process (never {@code null})
     * @see org.springframework.validation.ValidationUtils
     */
    @Override
    public void validate(final Object target, final Errors errors) {
        final FileUploadForm form = (FileUploadForm) target;
        final MultipartFile file = form.getFile();
        if (file.isEmpty()) {
            errors.rejectValue(WebKeys.FILE, "I0002");
            return;
        }

        if (StringUtils.isBlank(form.getPath())) {
            errors.rejectValue(WebKeys.PATH, "I0004");
        }
    }
}
