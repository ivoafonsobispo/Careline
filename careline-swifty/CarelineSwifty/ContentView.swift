//
//  ContentView.swift
//  CarelineSwifty
//
//  Created by formando on 16/11/2023.
//

import SwiftUI

struct SummaryView: View {
    var userName: String
    
    var body: some View {
        HStack(alignment: .center) {
            VStack(alignment: .leading) {
                Text("Summary")
                    .font(/*@START_MENU_TOKEN@*/.title/*@END_MENU_TOKEN@*/)
                    .fontWeight(/*@START_MENU_TOKEN@*/.bold/*@END_MENU_TOKEN@*/)
            }
            Text(userName)
                .font(.title2)
                .fontWeight(/*@START_MENU_TOKEN@*/.bold/*@END_MENU_TOKEN@*/)
                .frame(maxWidth: /*@START_MENU_TOKEN@*/.infinity/*@END_MENU_TOKEN@*/, alignment: .trailing)
        }
        .frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: 50)
    }
}

struct LastHeartbeatView: View {
    
    @State private var isAnimating = false
    var latestHeartbeatValue: Int
    var latestHeartbeatCreatedAt: String
    
    var animation: Animation {
        Animation.easeInOut(duration: 0.6) // Mudar o ritmo da animaçâo aqui
            .repeatForever(autoreverses: true)
    }
    
    var body: some View {
        HStack{
            VStack(alignment: .leading){
                Text("Last Heartbeat:")
                HStack{
                    Text(String(latestHeartbeatValue))
                        .font(.custom("", fixedSize: 50))
                        .fontWeight(.bold)
                    Text("BPM")
                }.padding(EdgeInsets(top: 0, leading: 20, bottom: 0, trailing: 20))
                Text (latestHeartbeatCreatedAt)
                    .foregroundColor(.gray)
                    .font(.caption)
            }
            Image("heartDT")
                .resizable()
                .scaleEffect(isAnimating ? 0.8 : 1.0)
                .frame(minWidth: 100, maxWidth: 100, minHeight: 90, maxHeight: 90)
                .onAppear{
                    DispatchQueue.main.async {
                        withAnimation(animation) {
                            isAnimating = true
                        }
                    }
                }
            
        }.padding(EdgeInsets(top: 20, leading: 20, bottom: 20, trailing: 20))
            .frame(minWidth:  0, maxWidth: .infinity, minHeight: 0, maxHeight: 130, alignment: .topLeading)
            .background(Color("Salmon"))
            .cornerRadius(15)
    }
}

struct MeasureTextView: View {
    var measures: [Measure]
    var token: String
    
    var body: some View {
        HStack{
            Text("Measure")
                .fontWeight(.bold)
            NavigationLink(destination: ShowMoreView(measures: measures, token:token)){
                Text("Show More")
            }.foregroundColor(.red)
                .frame(maxWidth: /*@START_MENU_TOKEN@*/.infinity/*@END_MENU_TOKEN@*/, alignment: .trailing)
        }.frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: 30)
            .padding(EdgeInsets(top: 20, leading: 0, bottom: 0, trailing: 0))
    }
}

struct MeasureButtonsListView: View{
    var measures: [Measure]
    var token: String
    
    var body: some View {
        ForEach(measures){measure in
            MeasureButtonView(measure: measure, token:token)
        }
        TriageButtonView(measures: measures, token:token)
    }
}

struct TriageButtonView: View{
    var measures: [Measure]
    var token: String
    
    var body: some View {
        NavigationLink(destination: TriageView(measures: measures, token:token)) {
            HStack{
                Image(systemName: "checkmark.square")
                    .resizable()
                    .scaledToFit()
                    .frame(width: 30.0,height: 30.0)
                Text("Triage")
                    .padding(EdgeInsets(top: 0, leading: 10, bottom: 0, trailing: 0))
                Text("RIGHT NOW >")
                    .foregroundColor(Color.gray)
                    .font(.caption)
                    .frame(maxWidth: /*@START_MENU_TOKEN@*/.infinity/*@END_MENU_TOKEN@*/, alignment: .trailing)
            }.frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: 30, alignment: .leading)
                .padding(EdgeInsets(top: 20, leading: 20, bottom: 20, trailing: 20))
                .foregroundColor(Color.black)
        }.background(Color("Salmon"))
            .padding(EdgeInsets(top: 0, leading: 0, bottom: 0, trailing: 0))
            .cornerRadius(15)
        
    }
}

struct MeasureButtonView: View{
    var measure: Measure
    var token: String
    
    var body: some View {
        NavigationLink(destination: MeasureView(measure: measure, token:token)) {
            HStack{
                Image(systemName: measure.symbol)
                    .resizable()
                    .scaledToFit()
                    .frame(width: 30.0,height: 30.0)
                Text(measure.name)
                    .padding(EdgeInsets(top: 0, leading: 10, bottom: 0, trailing: 0))
                    .frame(minWidth: 170, maxWidth: 170, alignment: .leading)
                Text("RIGHT NOW >")
                    .foregroundColor(Color.gray)
                    .font(.caption)
                    .frame(maxWidth: /*@START_MENU_TOKEN@*/.infinity/*@END_MENU_TOKEN@*/, alignment: .trailing)
            }.frame(minWidth: 0, maxWidth: .infinity, minHeight: 30, maxHeight: 30, alignment: .leading)
                .padding(EdgeInsets(top: 20, leading: 20, bottom: 20, trailing: 20))
                .foregroundColor(Color.black)
        }.background(Color("Salmon"))
            .padding(EdgeInsets(top: 0, leading: 0, bottom: 0, trailing: 0))
            .cornerRadius(15)
        
    }
}

struct ContentView: View {
    @StateObject var user = User()
    @State private var isLoggedIn = false
    
    @StateObject var apiToken = APIToken()
    @StateObject var apiGetLatestHeartbeat = APIHeartbeatGET()
    
    var body: some View {
        NavigationView{
            VStack{
                if isLoggedIn {
                    SummaryView(userName: user.name)
                        .onAppear{
                            apiGetLatestHeartbeat.bearerToken = apiToken.bearerToken
                            apiGetLatestHeartbeat.makeHeartbeatGetRequest { result in
                                switch result {
                                    case .success:
                                        print("Heartbeat request successful")
                                    case .failure(let error):
                                        print("Heartbeat request failed with error: \(error)")
                                }
                            }
                        }
                    Text("Quick Status")
                        .font(.title3)
                        .fontWeight(/*@START_MENU_TOKEN@*/.bold/*@END_MENU_TOKEN@*/)
                        .frame(maxWidth: .infinity,alignment: .leading)
                    LastHeartbeatView(latestHeartbeatValue:apiGetLatestHeartbeat.latestHeartbeatValue ?? 0, latestHeartbeatCreatedAt:apiGetLatestHeartbeat.latestHeartbeatCreatedAt ?? "")
                    MeasureTextView(measures: user.measures, token:user.token)
                    MeasureButtonsListView(measures: user.measures, token:user.token)
                } else {
                    LoginView(isLoggedIn: $isLoggedIn)
                        .environmentObject(apiToken)
                        .environmentObject(user)
                }
            }
            .frame(minWidth: /*@START_MENU_TOKEN@*/0/*@END_MENU_TOKEN@*/, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity, alignment: .topLeading)
            .navigationBarHidden(true)
            .navigationBarTitle("", displayMode: .inline)
        }.padding(EdgeInsets(top: 30, leading: 20, bottom: 40, trailing: 20))
        .foregroundColor(.black)
    }
}
