import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { AuthService } from './services/auth.service';

@NgModule({
  imports: [
    BrowserModule,
    HttpClientModule,
    AppComponent 
  ],
  providers: [AuthService],
})
export class AppModule { }