package flag.com.findairplane;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    LinearLayout Loout;
    LinearLayout L1=null;
    int x1,y1,x2,y2;
    int n=8;
    int[][] x=null;
    int[][] pick=null;
    int high=800;
    int times=0;
    int find=0;
    int f=0;
    int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newGame();
    }
    public void produce(final int X,final int Y){
        LinearLayout Lout= new LinearLayout(this);
        Loout.addView(Lout,(int)((float)high/(float) n),(int)((float)high/(float) n));
        Lout.setPadding(5,5,5,5);
        final LinearLayout Lin= new LinearLayout(this);
        Lin.setBackgroundColor(Color.parseColor("#DCDCDC"));
        Lout.addView(Lin,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);

        /*
        if(x[X][Y]==0)Lin.setBackgroundColor(Color.parseColor("#FFFFFF"));
        else if(x[X][Y]==1)Lin.setBackgroundColor(Color.parseColor("#1E90FF"));
        else if(x[X][Y]==2)Lin.setBackgroundColor(Color.parseColor("#FF0000"));
         */

        Lin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(pick[X][Y]==0) {
                    times++;
                    if (x[X][Y] == 0) Lin.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    else if (x[X][Y] == 1) Lin.setBackgroundColor(Color.parseColor("#1E90FF"));
                    else if (x[X][Y] == 2) Lin.setBackgroundColor(Color.parseColor("#FF0000"));
                    TextView tv = (TextView) findViewById(R.id.Times);
                    tv.setText(Integer.toString(times) + "次");

                    if (x[X][Y] == 2) {
                        find++;
                        if (find == 2) {
                            new AlertDialog.Builder(MainActivity.this)
                                    .setIcon(R.drawable.ic_launcher_background)
                                    .setTitle("Message")
                                    .setMessage("恭喜你成功找到了所有的機頭!\n你使用的次數是" + times + "次。")
                                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .show();
                        }
                    }
                }
                pick[X][Y]=1;
            }
        });
    }
    public void draw(int i,int j,int direction){
        if(direction==0){
            x[i-1][j]=2;
            for(int k=-2;k<=2;k++)x[i][j+k]=1;
            x[i+1][j]=1;
            for(int k=-1;k<=1;k++)x[i+2][j+k]=1;
        }
        else if(direction==1){
            x[i+1][j]=2;
            for(int k=-2;k<=2;k++)x[i][j+k]=1;
            x[i-1][j]=1;
            for(int k=-1;k<=1;k++)x[i-2][j+k]=1;
        }
        else if(direction==2){
            x[i][j-1]=2;
            for(int k=-2;k<=2;k++)x[i+k][j]=1;
            x[i][j+1]=1;
            for(int k=-1;k<=1;k++)x[i+k][j+2]=1;
        }
        else if(direction==3){
            x[i][j+1]=2;
            for(int k=-2;k<=2;k++)x[i+k][j]=1;
            x[i][j-1]=1;
            for(int k=-1;k<=1;k++)x[i+k][j-2]=1;
        }
    }

    public int tick(int i,int j,int direction) {
        if (direction == 0) {
            if (x[i - 1][j] != 0) return 1;
            for (int k = -2; k <= 2; k++) if (x[i][j + k] != 0) return 1;
            if (x[i + 1][j] != 0) return 1;
            for (int k = -1; k <= 1; k++) if (x[i + 2][j + k] != 0) return 1;
        } else if (direction == 1) {
            if (x[i + 1][j] != 0) return 1;
            for (int k = -2; k <= 2; k++) if (x[i][j + k] != 0) return 1;
            if (x[i - 1][j] != 0) return 1;
            for (int k = -1; k <= 1; k++) if (x[i - 2][j + k] != 0) return 1;
        } else if (direction == 2) {
            if (x[i][j - 1] != 0) return 1;
            for (int k = -2; k <= 2; k++) if (x[i + k][j] != 0) return 1;
            if (x[i][j + 1] != 0) return 1;
            for (int k = -1; k <= 1; k++) if (x[i + k][j + 2] != 0) return 1;
        } else if (direction == 3) {
            if (x[i][j + 1] != 0) return 1;
            for (int k = -2; k <= 2; k++) if (x[i + k][j] != 0) return 1;
            if (x[i][j - 1] != 0) return 1;
            for (int k = -1; k <= 1; k++) if (x[i + k][j - 2] != 0) return 1;
        }
        return 0;
    }

    public void newGame(){
        f=0;
        try{
            pick = new int[n][n];
            x = new int[n][n];
            Spinner spinner2 = (Spinner)findViewById(R.id.spinner_select);
            final String[] lunch2 = {"8x8","9x9","10x10"};
            f=0;flag=0;
            ArrayAdapter<String> lunchList2 = new ArrayAdapter<>(MainActivity.this,
                    android.R.layout.simple_spinner_dropdown_item,
                    lunch2);spinner2.setAdapter(lunchList2);
                    spinner2.setSelection(n-8);
            spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(f==1){
                        if(lunch2[position].equals("8x8")){
                            if(flag==1||n!=8) {
                                Toast.makeText(MainActivity.this, "你選擇了8x8的難度", Toast.LENGTH_SHORT).show();
                                n = 8;
                                flag=1;
                                newGame();
                            }
                        }
                        else if(lunch2[position].equals("9x9")){
                            if(flag==1||n!=9) {
                                Toast.makeText(MainActivity.this, "你選擇了9x9的難度", Toast.LENGTH_SHORT).show();
                                n = 9;
                                flag = 1;
                                newGame();
                            }

                        }
                        else if(lunch2[position].equals("10x10")){
                            if(flag==1||n!=10) {
                                Toast.makeText(MainActivity.this, "你選擇了10x10的難度", Toast.LENGTH_SHORT).show();
                                n = 10;
                                flag = 1;
                                newGame();
                            }
                        }
                        else{
                            if(flag==1||n!=8) {
                                Toast.makeText(MainActivity.this, "你選擇了8x8的難度", Toast.LENGTH_SHORT).show();
                                n = 8;
                                flag=1;
                                newGame();
                            }
                        }
                    }
                    f=1;
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            });

            for(int i=0;i<n;i++){
                for(int j=0;j<n;j++){
                    x[i][j]=0;
                    pick[i][j]=0;
                }
            }
            /*產生飛機*/
            //定中心點
            x1 = (int) (Math.random()*(n-2)+1);
            if(x1!=1&&x1!=(n-2))y1=(int) (Math.random()*(n-2)+1);
            else y1=(int) (Math.random()*(n-4)+2);
            //定方向
            int direct=0;//U=0,D=1,L=2,R=3
            if(x1==1)direct=1;
            else if(x1==(n-2))direct=1;
            else if(y1==1)direct=2;
            else if(y1==(n-2))direct=3;
            else direct=(int) (Math.random()*4);
            draw(x1,y1,direct);

            //第二架飛機
            int direct2=0;//U=0,D=1,L=2,R=3
            do{
                x2 = (int) (Math.random()*(n-2)+1);
                if(x2!=1&&x2!=(n-2))y2=(int) (Math.random()*(n-2)+1);
                else y2=(int) (Math.random()*(n-4)+2);
                //定方向
                if(x2==1)direct2=1;
                else if(x2==6)direct2=1;
                else if(y2==1)direct2=2;
                else if(y2==6)direct2=3;
                else direct2=(int) (Math.random()*4);
            }while(tick(x2,y2,direct2)==1);

            draw(x2,y2,direct2);
            times=0;
            find=0;
            LinearLayout LL =(LinearLayout)findViewById(R.id.LL);
            if(L1!=null)LL.removeView(L1);
            //產生格子
            L1= new LinearLayout(this);
            L1.setOrientation(LinearLayout.VERTICAL);
            LL.addView(L1, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
            for(int i=0;i<n;i++){
                Loout= new LinearLayout(this);
                Loout.setOrientation(LinearLayout.HORIZONTAL);
                L1.addView(Loout, LinearLayout.LayoutParams.MATCH_PARENT,(int)((float)high/(float) n));
                for(int j=0;j<n;j++)
                    produce(i,j);
            }
            TextView tv =(TextView)findViewById(R.id.Times);
            tv.setText(Integer.toString(times)+"次");

        }catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(this,"NewGame: "+e.toString(), Toast.LENGTH_SHORT).show();
            newGame();
        }
    }
    public  void  Hnit(View view) {
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        final View v = inflater.inflate(R.layout.layout, null);
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Hint")
                .setView(v)
                .setIcon(R.drawable.logo)
                .setMessage("飛機的形狀如下喔(*´▽`*)")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }
    public void NewGame(View view){
        newGame();
    }

}
