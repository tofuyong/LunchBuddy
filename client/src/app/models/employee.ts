export class Employee {
    constructor(
        public employeeId: number,
        public isFinding: boolean,
        public firstName: string,
        public lastName: string,
        public salutation: string,
        public gender: string,
        public email: string,
        public department: string,
        public title: string
    ) { };
}