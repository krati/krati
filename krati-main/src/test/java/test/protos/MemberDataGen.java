/*
 * Copyright (c) 2010-2012 LinkedIn, Inc
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package test.protos;

import test.protos.MemberProtos;
import test.protos.MemberProtos.Member;
import test.protos.MemberProtos.MemberBook;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MemberDataGen {
    public static MemberBook generateMemberBook(int memberCount) {
        MemberProtos.MemberBook.Builder bookBuilder = MemberProtos.MemberBook.newBuilder();
        
        for (int i = 0; i < memberCount; i++) {
            MemberProtos.Member.Builder builder = MemberProtos.Member.newBuilder();
            
            Member.PhoneNumber.Builder phoneNumber1 =
                Member.PhoneNumber.newBuilder().setNumber("1-800-123-456" + (i%3));
            phoneNumber1.setType(Member.PhoneType.MOBILE);
            
            Member.PhoneNumber.Builder phoneNumber2 =
                Member.PhoneNumber.newBuilder().setNumber("1-800-123-567" + (i%5));
            phoneNumber2.setType(Member.PhoneType.WORK);
            
            Member.PhoneNumber.Builder phoneNumber3 =
                Member.PhoneNumber.newBuilder().setNumber("1-800-123-678" + (i%7));
            phoneNumber3.setType(Member.PhoneType.MOBILE);
            
            builder.setMemberId(i)
                .setLastName("LastName"+i)
                .setFirstName("FirstName"+i)
                .addEmail("email"+i+"@gmail.com")
                .addPhone(phoneNumber1)
                .addPhone(phoneNumber2)
                .addPhone(phoneNumber3)
                .addJobTitle("Software Engineer " + (i%5))
                .addJobTitle("Technical Member Staff " + (i%3))
                .setProfile("Enjoy reading Foreign Affairs and watching movies.");
            
            bookBuilder.addMember(builder.build());
        }
        
        // Write the new address book back to disk.
        return bookBuilder.build();
    }
    
    /**
     * java test.protos.MemberDataGen test/seed/member.proto.dat 10000
     * 
     * @param args
     * @throws NumberFormatException
     * @throws IOException
     */
    public static void main(String[] args) throws NumberFormatException, IOException {
        File filePath = new File(args[0]);
        int memberCount = Integer.parseInt(args[1]);
        
        MemberBook memberBook = MemberDataGen.generateMemberBook(memberCount);
        FileOutputStream output = new FileOutputStream(filePath);
        memberBook.writeTo(output);
        output.close();
        
        System.out.println("Done");
    }
}
