/**
 *  Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.test.npe;

import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Test for correct handling of null checks.
 *
 * @author Sjaak Derksen
 */
@WithClasses( {
    SourceTargetMapper.class,
    NullObjectMapper.class,
    NullObject.class,
    MyBigIntMapper.class,
    MyBigIntWrapper.class,
    Source.class,
    Target.class
} )
@RunWith( AnnotationProcessorTestRunner.class )
public class NullPtrCheckTest {

    @IssueKey( "214" )
    @Test( expected = NullPointerException.class )
    public void shouldThrowNullptrWhenCustomMapperIsInvoked() {

        Source source = new Source();
        source.setNumber( "5" );
        source.setSomeInteger( 7 );
        SourceTargetMapper.INSTANCE.sourceToTarget( source );
    }

    @IssueKey( "214" )
    @Test
    public void shouldSurroundTypeConversionWithNPECheck() {

        Source source = new Source();
        source.setSomeObject( new NullObject() );
        source.setSomeInteger( 7 );
        Target target =  SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target.getNumber() ).isNull();

    }

    @IssueKey( "214" )
    @Test
    public void shouldSurroundArrayListConstructionWithNPECheck() {

        Source source = new Source();
        source.setSomeObject( new NullObject() );
        source.setSomeInteger( 7 );
        Target target =  SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target.getSomeList() ).isNull();
    }

    @IssueKey( "237" )
    @Test
    public void shouldMapMappedTypeConversion() {

        Source source = new Source();
        source.setSomeObject( new NullObject() );
        Target target =  SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target.getSomeList() ).isNull();
        assertThat( target.getSomeInteger() ).isNull();
    }

}
