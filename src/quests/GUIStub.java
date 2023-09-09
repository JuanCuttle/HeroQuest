package quests;

import services.HeroQuest;
import entities.Position;
import entities.tiles.Door;
import entities.tiles.FallingRock;
import entities.tiles.Furniture;
import entities.utils.Strings;
import enums.FurnitureDirectionEnum;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Objects;

public class GUIStub extends JFrame {

	private static final long serialVersionUID = 1L;
	protected JPanel contentPane;
	protected JButton[][] boardButtons;
	
	protected BasicMap map;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
            try {
                HeroQuest game = new HeroQuest();
                BasicMap map = new TheFireMage(game);
                GUIStub frame = new GUIStub(map);
                game.setMap(map);
                frame.setVisible(true);
                map.createMonsters(game);
                frame.refreshGameBoard();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
	}

	public GUIStub(BasicMap map) {
		this.map = map;
		
		setTitle(Strings.HEROQUEST.toString());

		this.boardButtons = new JButton[map.getTotalNumberOfRows()][map.getTotalNumberOfColumns()];

		// Configure the window
		setSize(1300, 770);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setBackground(Color.BLACK);
		setContentPane(contentPane);
		Border emptyBorder = BorderFactory.createEmptyBorder();
		setFocusable(true);
		requestFocusInWindow();
		
		for (int j = 0; j < map.getTotalNumberOfColumns(); j++) {
			JButton columnNumberButtons = new JButton();
			columnNumberButtons.setText(""+j);
			columnNumberButtons.setBounds(120 + (j * 23), 49 + (-1 * 23), 23, 23);
			columnNumberButtons.setBorder(emptyBorder);
			columnNumberButtons.setVisible(true);
			contentPane.add(columnNumberButtons);
		}
		for (int i = 0; i < map.getTotalNumberOfRows(); i++) {
			JButton rowNumberButtons = new JButton();
			rowNumberButtons.setText(""+i);
			rowNumberButtons.setBounds(120 + (-1 * 23), 49 + (i * 23), 23, 23);
			rowNumberButtons.setBorder(emptyBorder);
			rowNumberButtons.setVisible(true);
			contentPane.add(rowNumberButtons);
		}

		// Create the board's buttons
		for (int i = 0; i < map.getTotalNumberOfRows(); i++) {
			for (int j = 0; j < map.getTotalNumberOfColumns(); j++) {
				JButton boardButtons = new JButton();
				boardButtons.setName("" + i + j);
				boardButtons.setBounds(120 + (j * 23), 49 + (i * 23), 23, 23);
				boardButtons.setBorder(emptyBorder);
				boardButtons.setVisible(true);
				boardButtons.addKeyListener(null);
				boardButtons.addActionListener(e -> {
                });
				contentPane.add(boardButtons);
				this.boardButtons[i][j] = boardButtons;
			}
		}
		
		refreshGameBoard();
	}
	
	public void refreshTile(JButton button, Position position) {
		String path;
		int positionRow = position.getRow();
		int positionColumn = position.getColumn();
		if (!position.isVisible()) {
			path = "/images/" + "Wall" + ".png";

		} else {
			if (position.getCreature() != null) {
				path = "/images/"
						+ position.getCreature().getClass().getSimpleName()
						+ ".png";

			} else if (position.getTrap() != null) {
					if (position.getTrap().getVisible()) {
						if (position.getTrap() instanceof FallingRock && position.getTrap().getTriggered()){
							path = "/images/"
									+ "Rubble"
									+ ".png";
						} else {
							path = "/images/"
									+ position.getTrap().getClass().getSimpleName()
									+ ".png";
						}
					} else {
						path = "/images/" + position.getClass().getSimpleName()
								+ ".png";
					}
			} else {
				if (position instanceof Door) {
					if (!((Door) position).isSecret()) {
						if (((Door) position).isOpen()) {
							path = "/images/tiles/doors/OpenDoor.png";
						} else {
							path = "/images/tiles/doors/ClosedDoor.png";
						}
					} else {
						path = "/images/" + "Wall" + ".png";
					}
				} else {
					path = "/images/" + position.getClass().getSimpleName()
							+ ".png";
				}
				int stairRow = map.getStairsPosition()[0];
				int stairColumn = map.getStairsPosition()[1];
				if (stairRow != 0){
					if (positionRow == stairRow && positionColumn == stairColumn) {
						path = "/images/stairs/Stairs00.png";
					} else if (positionRow == stairRow && positionColumn == stairColumn+1) {
						path = "/images/stairs/Stairs01.png";
					} else if (positionRow == stairRow+1 && positionColumn == stairColumn) {
						path = "/images/stairs/Stairs10.png";
					} else if (positionRow == stairRow+1 && positionColumn == stairColumn+1) {
						path = "/images/stairs/Stairs11.png";
					}
				}
				
				if (map.getPosition((byte)positionRow, (byte)positionColumn) instanceof Furniture){
					
					byte[] furnitureSourcePosition = map.getTable1Position();
					if (furnitureSourcePosition != null) {
						path = generateTablePath(positionRow, positionColumn, furnitureSourcePosition);
					}
					furnitureSourcePosition = map.getTable2Position();
					if (furnitureSourcePosition != null) {
						path = generateTablePath(positionRow, positionColumn, furnitureSourcePosition);
					}
					
					furnitureSourcePosition = map.getRackPosition();
					if (furnitureSourcePosition != null) {
						path = generateRackPath(positionRow, positionColumn, furnitureSourcePosition);
					}
					
					furnitureSourcePosition = map.getBookOnTablePosition();
					if (furnitureSourcePosition != null) {
						path = generateBookOnTablePath(positionRow, positionColumn, furnitureSourcePosition);
					}
					
					furnitureSourcePosition = map.getTombPosition();
					if (furnitureSourcePosition != null) {
						path = generateTombPath(positionRow, positionColumn, furnitureSourcePosition);
					}
					
					furnitureSourcePosition = map.getThronePosition();
					if (furnitureSourcePosition != null) {
						path = generateThronePath(positionRow, positionColumn, furnitureSourcePosition);
					}
					
					furnitureSourcePosition = map.getWepRackPosition();
					if (furnitureSourcePosition != null) {
						path = generateWeaponRackPath(positionRow, positionColumn, furnitureSourcePosition);
					}
					
					furnitureSourcePosition = map.getDeskPosition();
					if (furnitureSourcePosition != null) {
						path = generateDeskPath(positionRow, positionColumn, furnitureSourcePosition);
					}
					
					furnitureSourcePosition = map.getFireplacePosition();
					if (furnitureSourcePosition != null) {
						path = generateFireplacePath(positionRow, positionColumn, furnitureSourcePosition);
					}
					
					furnitureSourcePosition = map.getBookcase1Position();
					if (furnitureSourcePosition != null) {
						path = bookcasePath(positionRow, positionColumn, furnitureSourcePosition);
					}
					
					furnitureSourcePosition = map.getBookcase2Position();
					if (furnitureSourcePosition != null) {
						path = bookcasePath(positionRow, positionColumn, furnitureSourcePosition);
					}
					
					furnitureSourcePosition = map.getBookcase3Position();
					if (furnitureSourcePosition != null) {
						path = bookcasePath(positionRow, positionColumn, furnitureSourcePosition);
					}
				}
			}
		}
		ImageIcon img = new ImageIcon(Objects.requireNonNull(getClass().getResource(path)));
		button.setIcon(img);
		button.invalidate();
		button.revalidate();
		button.repaint();
	}
	
	private String generateTablePath(int row, int column, byte[] tablePosition) {
		String path = null;
		int tableRow = tablePosition[1];
		int tableColumn = tablePosition[2];
		
		if (tablePosition[0] == FurnitureDirectionEnum.HORIZONTAL.getId()) {
			if (row == tableRow && column == tableColumn) {
				path = "/images/tables/TableH00.png";
			} else if (row == tableRow && column == tableColumn+1) {
				path = "/images/tables/TableH01.png";
			} else if (row == tableRow && column == tableColumn+2) {
				path = "/images/tables/TableH02.png";
			} else if (row == tableRow+1 && column == tableColumn) {
				path = "/images/tables/TableH10.png";
			} else if (row == tableRow+1 && column == tableColumn+1) {
				path = "/images/tables/TableH11.png";
			} else if (row == tableRow+1 && column == tableColumn+2) {
				path = "/images/tables/TableH12.png";
			}
		} else {
			if (row == tableRow && column == tableColumn) {
				path = "/images/tables/TableV00.png";
			} else if (row == tableRow && column == tableColumn+1) {
				path = "/images/tables/TableV01.png";
			} else if (row == tableRow+1 && column == tableColumn) {
				path = "/images/tables/TableV10.png";
			} else if (row == tableRow+1 && column == tableColumn+1) {
				path = "/images/tables/TableV11.png";
			} else if (row == tableRow+2 && column == tableColumn) {
				path = "/images/tables/TableV20.png";
			} else if (row == tableRow+2 && column == tableColumn+1) {
				path = "/images/tables/TableV21.png";
			}
		}
		return path;
	}
	
	private String generateRackPath(int row, int column, byte[] rackPosition) {
		String path = null;
		int rackRow = rackPosition[1];
		int rackColumn = rackPosition[2];
		
		if (rackPosition[0] == FurnitureDirectionEnum.HORIZONTAL.getId()) {
			if (row == rackRow && column == rackColumn) {
				path = "/images/racks/RackH00.png";
			} else if (row == rackRow && column == rackColumn+1) {
				path = "/images/racks/RackH01.png";
			} else if (row == rackRow && column == rackColumn+2) {
				path = "/images/racks/RackH02.png";
			} else if (row == rackRow+1 && column == rackColumn) {
				path = "/images/racks/RackH10.png";
			} else if (row == rackRow+1 && column == rackColumn+1) {
				path = "/images/racks/RackH11.png";
			} else if (row == rackRow+1 && column == rackColumn+2) {
				path = "/images/racks/RackH12.png";
			}
		} else {
			if (row == rackRow && column == rackColumn) {
				path = "/images/racks/RackV00.png";
			} else if (row == rackRow && column == rackColumn+1) {
				path = "/images/racks/RackV01.png";
			} else if (row == rackRow+1 && column == rackColumn) {
				path = "/images/racks/RackV10.png";
			} else if (row == rackRow+1 && column == rackColumn+1) {
				path = "/images/racks/RackV11.png";
			} else if (row == rackRow+2 && column == rackColumn) {
				path = "/images/racks/RackV20.png";
			} else if (row == rackRow+2 && column == rackColumn+1) {
				path = "/images/racks/RackV21.png";
			}
		}
		return path;
	}
	
	private String generateBookOnTablePath(int row, int column, byte[] bookOnTablePosition) {
		String path = null;
		int botRow = bookOnTablePosition[1];
		int botColumn = bookOnTablePosition[2];
		
		if (bookOnTablePosition[0] == FurnitureDirectionEnum.HORIZONTAL.getId()) {
			if (row == botRow && column == botColumn) {
				path = "/images/bookOnTable/BookOnTable01.png";
			} else if (row == botRow && column == botColumn+1) {
				path = "/images/bookOnTable/BookOnTable11.png";
			} else if (row == botRow && column == botColumn+2) {
				path = "/images/bookOnTable/BookOnTable21.png";
			} else if (row == botRow+1 && column == botColumn) {
				path = "/images/bookOnTable/BookOnTable00.png";
			} else if (row == botRow+1 && column == botColumn+1) {
				path = "/images/bookOnTable/BookOnTable10.png";
			} else if (row == botRow+1 && column == botColumn+2) {
				path = "/images/bookOnTable/BookOnTable20.png";
			}
		} else {
			if (row == botRow && column == botColumn) {
				path = "/images/bookOnTable/BookOnTable00.png";
			} else if (row == botRow && column == botColumn+1) {
				path = "/images/bookOnTable/BookOnTable01.png";
			} else if (row == botRow+1 && column == botColumn) {
				path = "/images/bookOnTable/BookOnTable10.png";
			} else if (row == botRow+1 && column == botColumn+1) {
				path = "/images/bookOnTable/BookOnTable11.png";
			} else if (row == botRow+2 && column == botColumn) {
				path = "/images/bookOnTable/BookOnTable20.png";
			} else if (row == botRow+2 && column == botColumn+1) {
				path = "/images/bookOnTable/BookOnTable21.png";
			}
		}
		return path;
	}
	
	private String generateTombPath(int row, int column, byte[] tombPosition) {
		String path = null;
		int tombRow = tombPosition[1];
		int tombColumn = tombPosition[2];
		
		if (tombPosition[0] == FurnitureDirectionEnum.HORIZONTAL.getId()) {
			if (row == tombRow && column == tombColumn) {
				path = "/images/tomb/Tomb01.png";
			} else if (row == tombRow && column == tombColumn+1) {
				path = "/images/tomb/Tomb11.png";
			} else if (row == tombRow && column == tombColumn+2) {
				path = "/images/tomb/Tomb21.png";
			} else if (row == tombRow+1 && column == tombColumn) {
				path = "/images/tomb/Tomb00.png";
			} else if (row == tombRow+1 && column == tombColumn+1) {
				path = "/images/tomb/Tomb10.png";
			} else if (row == tombRow+1 && column == tombColumn+2) {
				path = "/images/tomb/Tomb20.png";
			}
		} else {
			if (row == tombRow && column == tombColumn) {
				path = "/images/tomb/Tomb00.png";
			} else if (row == tombRow && column == tombColumn+1) {
				path = "/images/tomb/Tomb01.png";
			} else if (row == tombRow+1 && column == tombColumn) {
				path = "/images/tomb/Tomb10.png";
			} else if (row == tombRow+1 && column == tombColumn+1) {
				path = "/images/tomb/Tomb11.png";
			} else if (row == tombRow+2 && column == tombColumn) {
				path = "/images/tomb/Tomb20.png";
			} else if (row == tombRow+2 && column == tombColumn+1) {
				path = "/images/tomb/Tomb21.png";
			}
		}
		return path;
	}
	
	private String generateThronePath(int row, int column, byte[] thronePosition) {
		String path = null;
		int throneRow = thronePosition[1];
		int throneColumn = thronePosition[2];
		
		if (thronePosition[0] == FurnitureDirectionEnum.RIGHT.getId()) {
			if (row == throneRow && column == throneColumn) {
				path = "/images/throne/ThroneR.png";
			}
		} else {
			if (row == throneRow && column == throneColumn) {
				path = "/images/throne/ThroneL.png";
			}
		}
		return path;
	}
	
	private String generateWeaponRackPath(int row, int column, byte[] weaponRackPosition) {
		String path = null;
		int weaponRackRow = weaponRackPosition[1];
		int weaponRackColumn = weaponRackPosition[2];
		
		if (weaponRackPosition[0] == FurnitureDirectionEnum.RIGHT.getId()) {
			if (row == weaponRackRow && column == weaponRackColumn) {
				path = "/images/wepRack/WepRackR0.png";
			} else if(row == weaponRackRow+1 && column == weaponRackColumn) {
				path = "/images/wepRack/WepRackR1.png";
			} else if(row == weaponRackRow+2 && column == weaponRackColumn) {
				path = "/images/wepRack/WepRackR2.png";
			}
		} else {
			if (row == weaponRackRow && column == weaponRackColumn) {
				path = "/images/wepRack/WepRackL0.png";
			} else if(row == weaponRackRow+1 && column == weaponRackColumn) {
				path = "/images/wepRack/WepRackL1.png";
			} else if(row == weaponRackRow+2 && column == weaponRackColumn) {
				path = "/images/wepRack/WepRackL2.png";
			}
		}
		return path;
	}
	
	private String generateDeskPath(int row, int column, byte[] deskPosition) {
		String path = null;
		int deskRow = deskPosition[1];
		int deskColumn = deskPosition[2];
		
		if (deskPosition[0] == FurnitureDirectionEnum.RIGHT.getId()) {
			if (row == deskRow && column == deskColumn) {
				path = "/images/desk/DeskR00.png";
			} else if (row == deskRow && column == deskColumn+1) {
				path = "/images/desk/DeskR01.png";
			} else if (row == deskRow+1 && column == deskColumn) {
				path = "/images/desk/DeskR10.png";
			} else if (row == deskRow+1 && column == deskColumn+1) {
				path = "/images/desk/DeskR11.png";
			} else if (row == deskRow+2 && column == deskColumn) {
				path = "/images/desk/DeskR20.png";
			} else if (row == deskRow+2 && column == deskColumn+1) {
				path = "/images/desk/DeskR21.png";
			}
		} else {
			if (row == deskRow && column == deskColumn) {
				path = "/images/desk/DeskL00.png";
			} else if (row == deskRow && column == deskColumn+1) {
				path = "/images/desk/DeskL01.png";
			} else if (row == deskRow+1 && column == deskColumn) {
				path = "/images/desk/DeskL10.png";
			} else if (row == deskRow+1 && column == deskColumn+1) {
				path = "/images/desk/DeskL11.png";
			} else if (row == deskRow+2 && column == deskColumn) {
				path = "/images/desk/DeskL20.png";
			} else if (row == deskRow+2 && column == deskColumn+1) {
				path = "/images/desk/DeskL21.png";
			}
		}
		return path;
	}
	
	private String generateFireplacePath(int row, int column, byte[] fireplacePosition) {
		String path = null;
		int fireplaceRow = fireplacePosition[1];
		int fireplaceColumn = fireplacePosition[2];
		
		if (fireplacePosition[0] == FurnitureDirectionEnum.DOWN.getId()) {
			if (row == fireplaceRow && column == fireplaceColumn) {
				path = "/images/fireplace/FireplaceD0.png";
			} else if(row == fireplaceRow && column == fireplaceColumn+1) {
				path = "/images/fireplace/FireplaceD1.png";
			} else if(row == fireplaceRow && column == fireplaceColumn+2) {
				path = "/images/fireplace/FireplaceD2.png";
			}
		} else {
			if (row == fireplaceRow && column == fireplaceColumn) {
				path = "/images/fireplace/FireplaceU0.png";
			} else if(row == fireplaceRow && column == fireplaceColumn+1) {
				path = "/images/fireplace/FireplaceU1.png";
			} else if(row == fireplaceRow && column == fireplaceColumn+2) {
				path = "/images/fireplace/FireplaceU2.png";
			}
		}
		return path;
	}
	
	private String bookcasePath(int row, int column, byte[] bookcasePosition) {
		String path = null;
		int bookcaseRow = bookcasePosition[1];
		int bookcaseColumn = bookcasePosition[2];
		
		if (bookcasePosition[0] == FurnitureDirectionEnum.HORIZONTAL.getId()) {
			if (row == bookcaseRow && column == bookcaseColumn) {
				path = "/images/bookcase/BookcaseH0.png";
			} else if(row == bookcaseRow && column == bookcaseColumn+1) {
				path = "/images/bookcase/BookcaseH1.png";
			} else if(row == bookcaseRow && column == bookcaseColumn+2) {
				path = "/images/bookcase/BookcaseH2.png";
			}
		} else {
			if (row == bookcaseRow && column == bookcaseColumn) {
				path = "/images/bookcase/BookcaseV0.png";
			} else if(row == bookcaseRow+1 && column == bookcaseColumn) {
				path = "/images/bookcase/BookcaseV1.png";
			} else if(row == bookcaseRow+2 && column == bookcaseColumn) {
				path = "/images/bookcase/BookcaseV2.png";
			}
		}
		return path;
	}

	public void refreshGameBoard() {
		for (byte i = 0; i < map.getTotalNumberOfRows(); i++) {
			for (byte j = 0; j < map.getTotalNumberOfColumns(); j++) {
				Position position = this.map.getPosition(i, j);
				this.refreshTile(this.boardButtons[i][j], position);
			}
		}
	}
}
