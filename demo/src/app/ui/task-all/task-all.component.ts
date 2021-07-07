import { Component, OnInit } from '@angular/core';
import { Task } from 'src/app/backend/task.model';
import { BackendService } from '../../service/backend-service';

@Component({
  selector: 'app-task-all',
  templateUrl: './task-all.component.html',
  styleUrls: ['./task-all.component.css']
})
export class TaskAllComponent implements OnInit {
  tasks: Task[] = [];

  constructor(private backendService:BackendService) { }

  ngOnInit(): void {
    let id :number =1;
    let desc:string ="testomg";
   let task:Task = {
    id: id,
    description : desc
   };
   console.log(task);
   this.getTaks();
  
  }
   updateTaskEventHandler($event:any){

    if ($event){
      this.getTaks();
    }
     console.log($event);

   }
   private getTaks(){
    
    this.backendService.getTasks().subscribe(data => {
      this.tasks = data;
      console.log(data);
    });
   }

}
