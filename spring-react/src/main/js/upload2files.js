const React = require('react');
const ReactDOM = require('react-dom');

class Upload2files extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          description: '',
          selectedFile: []
        };
        this.handleUploadImage = this.handleUploadImage.bind(this);

      }
      handleUploadImage(ev) {
            ev.preventDefault();

            const data = new FormData();
            data.append('file', this.uploadInput.files);

            fetch('multiplefileupload', {
            method: 'POST',
            body: data,
            }).then((response) => {
            response.json().then((body) => {
                this.setState({ imageURL: `http://localhost:8000/${body.file}` });
            });
            });
    }
   
    render() {
        return ( 
            <div>
                <form onSubmit={this.handleUploadImage} >
                    <div>   
                        <input ref={(ref) => { this.uploadInput = ref; }} type="file" />
                    </div>
                    <div>
                        <input ref={(ref) => { this.uploadInput = ref; }} type="file" />
                    </div>
                    <button type="submit" className="btn btn-primary">Send data!</button>
                </form>
            </div>
        )
    }

}


ReactDOM.render(
	<Upload2files />,
	document.getElementById('app')
)