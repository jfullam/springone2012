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
package springone.jsr352.sample.football;

import java.util.ArrayList;

import javax.batch.annotation.BatchProperty;
import javax.batch.annotation.Close;
import javax.batch.annotation.GetCheckpointInfo;
import javax.batch.annotation.ItemWriter;
import javax.batch.annotation.Open;
import javax.batch.annotation.WriteItems;

import org.springframework.batch.sample.domain.football.Player;
import org.springframework.batch.sample.domain.football.internal.PlayerItemWriter;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;

import jsr352.tck.chunktypes.CheckpointData;
import jsr352.tck.chunktypes.ReadRecord;


@ItemWriter("playerItemWriter")
public class SpringItemWriterAdaptor<T> {
	
	@BatchProperty(name="writer.spring.id")
	String springId;
	
	private org.springframework.batch.item.ItemWriter<T> springItemWriter;
	
	@Open
	public void openWriter(CheckpointData checkpointData) throws Exception {
		System.out.println("openWriter");
		 BeanFactoryLocator beanFactoryLocator = SingletonBeanFactoryLocator.getInstance();
		 BeanFactoryReference beanFactoryRef = beanFactoryLocator.useBeanFactory("springone.jsr352.beanfactory");
		 springItemWriter = (org.springframework.batch.item.ItemWriter<T>) beanFactoryRef.getFactory().getBean(springId);
	}
	
	@Close
	public void closeWriter() throws Exception {
		System.out.println("closeWriter");
	}
	
	@WriteItems
	public void writeItems(ArrayList<T> items) throws Exception {
		System.out.println("WRITE:  " + items);
		springItemWriter.write(items);
	}
	
	@GetCheckpointInfo
	public CheckpointData getCPD() throws Exception {
		return new CheckpointData();
	}
}
