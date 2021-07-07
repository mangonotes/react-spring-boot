import { Injectable } from "@angular/core";
import { BASE_URL, TASK } from "./service-const";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Task } from "../backend/task.model";
@Injectable()
export class BackendService{
   constructor(private http:HttpClient){

   }
   
   getTasks(): Observable<Task[]> {
     return  this.http.get<Task[]>(BASE_URL+TASK);
      
   }

   createTask(task:Task) :Observable<Task>{
     return this.http.post<Task>(BASE_URL+TASK,task);
   }

}