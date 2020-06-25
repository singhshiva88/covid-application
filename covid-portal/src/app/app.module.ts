import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import { CovidHomeComponent } from './covid-home/covid-home.component';
import {NgxChartsModule} from '@swimlane/ngx-charts';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {TableModule} from 'primeng/table';
import { HomeTabComponent } from './home-tab/home-tab.component';
import { AboutTabComponent } from './about-tab/about-tab.component';
import { LandingControllerComponent } from './landing-controller/landing-controller.component';
import { ReadMeComponent } from './read-me/read-me.component';

@NgModule({
  declarations: [
    AppComponent,
    CovidHomeComponent,
    HomeTabComponent,
    AboutTabComponent,
    LandingControllerComponent,
    ReadMeComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    NgxChartsModule,
    BrowserAnimationsModule,
    TableModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
