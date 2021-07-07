import { Component, OnInit, Output,EventEmitter } from '@angular/core';
import {NgForm} from '@angular/forms';
import { BackendService } from  '../../service/backend-service';
import {Task} from '../../backend/task.model';


@Component({
  selector: 'app-task-create',
  templateUrl: './task-create.component.html',
  styleUrls: ['./task-create.component.css']
})
export class TaskCreateComponent implements OnInit {
  @Output() updateEvent = new EventEmitter<boolean>();

 model :Task ={
  id: 0,
  description: ""
}
  constructor(private backendService: BackendService) { }
  onSubmit(myForm:NgForm){
    console.log(myForm.value);
    this.backendService.createTask(myForm.value).subscribe(data => {
      console.log("return data " , data);
      this.updateEvent.emit(true);
    })

  }

  ngOnInit(): void {
  }

}
