import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientInMemoryWebApiModule } from 'angular-in-memory-web-api';
import { FakeBackendService } from './backend/faked-bakend';
import { TaskAllComponent } from './ui/task-all/task-all.component';
import { HttpClientModule } from '@angular/common/http';
import { BackendService } from './service/backend-service';
import { TaskViewComponent } from './ui/task-view/task-view.component';
import { TaskCreateComponent } from './ui/task-create/task-create.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    AppComponent,
    TaskAllComponent,
    TaskViewComponent,
    TaskCreateComponent
  ],
  imports: [
    BrowserModule,
    CommonModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    HttpClientInMemoryWebApiModule.forRoot(FakeBackendService)

  ],
  providers: [BackendService],
  bootstrap: [AppComponent]
})
export class AppModule { }
