/*
 * Copyright 2012 International Business Machines Corp. 
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership. Licensed under the Apache License, 
 * Version 2.0 (the "License"); you may not use this file except in compliance 
 * with the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.batch.item.file;

import javax.batch.annotation.BatchProperty;
import javax.batch.annotation.Close;
import javax.batch.annotation.GetCheckpointInfo;
import javax.batch.annotation.ItemReader;
import javax.batch.annotation.Open;
import javax.batch.annotation.ReadItem;

import jsr352.tck.chunktypes.CheckpointData;

import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;


@ItemReader
public class SpringFlatFileItemReaderAdaptor<T> {
		
		@BatchProperty(name="reader.spring.id")
		String springId;
		
		private FlatFileItemReader<T> springItemReader;
			
		@Close
		public void close() throws Exception {
			springItemReader.doClose();
		}
	
		
		@Open
		public void openFile(CheckpointData cpd) throws Exception {
			 BeanFactoryLocator beanFactoryLocator = SingletonBeanFactoryLocator.getInstance();
			 BeanFactoryReference beanFactoryRef = beanFactoryLocator.useBeanFactory("springone.jsr352.beanfactory");
			 springItemReader = (FlatFileItemReader<T>) beanFactoryRef.getFactory().getBean(springId);
			 
			 springItemReader.doOpen();
		}



		@ReadItem
		public T readItem() throws UnexpectedInputException, ParseException, NonTransientResourceException, Exception {
			return springItemReader.doRead();
		}
		
		@GetCheckpointInfo
		public CheckpointData getCPD() {
		    return new CheckpointData();
		}

}
