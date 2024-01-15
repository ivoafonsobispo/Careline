//
//  ContentView.swift
//  CarelineSwifty
//
//  Created by formando on 12/01/2023.
//

import SwiftUI

struct MyTextFieldStyle: TextFieldStyle {
    func _body(configuration: TextField<Self._Label>) -> some View {
        configuration
            .padding(10)
            .overlay(RoundedRectangle(cornerRadius: 20, style: .continuous).stroke(Color.black, lineWidth: 1)
            ).padding()
    }
}

struct LoginView: View {
    
    @State var nus = ""
    @State var password = ""
    @State var loginFailed = false
    
    @State var nusInvalid = false
    
    @Binding var isLoggedIn: Bool
    @EnvironmentObject var apiToken: APIToken
    @EnvironmentObject var user: User
    
    var body: some View {
        VStack{
            TextField("NUS", text: $nus)
                .textContentType(.telephoneNumber)
                .padding([.leading, .trailing], 10)
                .padding(.bottom, 5)
                .textFieldStyle(MyTextFieldStyle())
                .onChange(of: nus) { newValue in
                    loginFailed = false
                    validateNUS(newValue)
                }
            
            if nusInvalid {
                Text("NUS invalid - it has to contain 9 digits (0 - 9).")
                    .foregroundColor(.red)
            }
            
            SecureField("Password", text: $password)
                .textContentType(.password)
                .padding([.leading, .trailing], 10)
                .padding(.bottom, 10)
                .textFieldStyle(MyTextFieldStyle())
                .onChange(of: password) { _ in
                    loginFailed = false
                }
            
            if loginFailed {
                Text("NUS or password invalid.")
                    .foregroundColor(.red)
            }
            
            Button(action: {signIn()}) {
                Text("Sign In")
                    .padding(EdgeInsets(top: 10, leading: 40, bottom: 10, trailing: 40))
            }.background(nus.isEmpty || password.isEmpty || nusInvalid ? Color.lightGray : Color("Salmon"))
                .foregroundColor(nus.isEmpty || password.isEmpty ? Color.gray : Color.black)
                .cornerRadius(15)
                .disabled(nus.isEmpty || password.isEmpty || nusInvalid)
        
        }
        .frame(minWidth: /*@START_MENU_TOKEN@*/0/*@END_MENU_TOKEN@*/, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity, alignment: .topLeading)
        .padding(EdgeInsets(top: 30, leading: 20, bottom: 40, trailing: 20))
        .foregroundColor(.black)
    }
    
    func signIn(){
        apiToken.signInPatient(nus: self.nus, password: self.password) { error in
            if let error = error {
                print("Error signing in: \(error)")
                loginFailed = true
            } else {
                
                print("Bearer token received:", apiToken.bearerToken ?? "No token received")
                user.token = apiToken.bearerToken ?? ""
                
                isLoggedIn = true
            }
        }
    }
    
    private func validateNUS(_ newValue: String){
        let isValid = newValue.rangeOfCharacter(from: CharacterSet.decimalDigits.inverted) == nil && newValue.count == 9
        
        nusInvalid = !isValid
    }
}
